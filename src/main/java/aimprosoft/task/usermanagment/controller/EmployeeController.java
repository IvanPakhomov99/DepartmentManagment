package aimprosoft.task.usermanagment.controller;

import aimprosoft.task.usermanagment.configuration.DaoFactory;
import aimprosoft.task.usermanagment.entity.Department;
import aimprosoft.task.usermanagment.entity.Employee;
import aimprosoft.task.usermanagment.repository.DepartmentDao;
import aimprosoft.task.usermanagment.repository.EmployeeDao;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


// TODO: обновление(исправить дату)
// TODO: добавить корректную Валидацию данных
// TODO: Проверить все Exceptions, создать своё
// TODO: Проверить весь проект, чтобы всё работало и подумать как можно незначительно улучшить
@WebServlet("/employee/*")
public class EmployeeController extends HttpServlet {
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        employeeDao = DaoFactory.getInstance().getEmployeeDao();
        departmentDao = DaoFactory.getInstance().getDepartmentDao();
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        try {
            switch (url) {
                case "/employee/new":
                    showNewEmployeeForm(req, resp);
                    break;
                case "/employee/insert":
                    insertEmployee(req, resp);
                    break;
                case "/employee/delete":
                    deleteEmployee(req, resp);
                    break;
                case "/employee/update":
                    updateEmployeeForm(req, resp);
                    break;
                case "/employee/edit":
                    getEmployee(req, resp);
                    break;
                default:
                    listEmployee(req, resp);
                    break;
            }
        } catch (SQLException e) {
            resp.sendRedirect("/WEB-INF/JSP/Error.jsp");
        }
    }

    private void listEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Department> departmentList = (List<Department>) departmentDao.findAll();
        String depName = req.getParameter("depName");
        List<Employee> employeeList;
        employeeList = (List<Employee>) employeeDao.findByDepId(depName);
        req.setAttribute("depName", depName);
        req.setAttribute("employeeList", employeeList);
        req.setAttribute("departmentList", departmentList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/JSP/EmployeeList.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void getEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        Employee employee = employeeDao.find(id);
        String employeeJsonString = this.gson.toJson(employee);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(employeeJsonString);
    }

    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException{
        Long id = Long.parseLong(req.getParameter("id"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Date birthday = Date.valueOf(req.getParameter("birthday"));
        String email = req.getParameter("email");
        int salary = Integer.parseInt(req.getParameter("salary"));
        String departmentName = req.getParameter("departmentName");
        Employee employee = new Employee(id, firstName, lastName, email, birthday, salary, departmentName);
        employeeDao.update(employee);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        String depName = req.getParameter("depName");
        Long id = Long.parseLong(req.getParameter("id"));
        Employee employee = new Employee(id);
        employeeDao.delete(employee);
        if (depName != null) {
            req.setAttribute("depName", depName);
        }
        listEmployee(req, resp);
    }

    private void insertEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException{

            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            Date birthday = Date.valueOf(req.getParameter("birthday"));
            int salary = Integer.parseInt(req.getParameter("salary"));
            String depName = req.getParameter("departmentName");
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setBirthday(birthday);
            employee.setEmail(email);
            employee.setSalary(salary);
            employee.setDepName(depName);
            employeeDao.create(employee);

    }

    private void showNewEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String depName = req.getParameter("depName");
        if (depName != null) {
            req.setAttribute("depName", depName);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/JSP/EmployeeForm.jsp");
        requestDispatcher.forward(req, resp);
    }
}
