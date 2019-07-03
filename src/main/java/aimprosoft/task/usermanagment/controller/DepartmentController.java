package aimprosoft.task.usermanagment.controller;

import aimprosoft.task.usermanagment.configuration.DaoFactory;
import aimprosoft.task.usermanagment.entity.Department;
import aimprosoft.task.usermanagment.repository.DepartmentDao;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/department/*")
public class DepartmentController extends HttpServlet {
    private DepartmentDao departmentDao;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        departmentDao = DaoFactory.getInstance().getDepartmentDao();
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
                case "/department/new":
                    showNewForm(req, resp);
                    break;
                case "/department/insert":
                    insertDepartment(req, resp);
                    break;
                case "/department/delete":
                    deleteDepartment(req, resp);
                    break;
                case "/department/update":
                    updateDepartment(req, resp);
                    break;
                case "/department/edit":
                    editDepartment(req, resp);
                    break;
                default:
                    listDepartment(req, resp);
                    break;
            }
        } catch (SQLException e) {
            resp.sendRedirect("/WEB-INF/JSP/Error.jsp");
        }
    }


    private void editDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        Department department = departmentDao.find(id);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/JSP/DepartmentForm.jsp");
        req.setAttribute("department", department);
        requestDispatcher.forward(req, resp);
    }

    private void updateDepartment(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        Department department = new Department(name, city, id);
        departmentDao.update(department);
        resp.sendRedirect("../department/list");
    }

    private void listDepartment(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        List<Department> departmentList = (List<Department>) departmentDao.findAll();
        req.setAttribute("departmentList", departmentList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/JSP/DepartmentList.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/JSP/DepartmentForm.jsp");
        dispatcher.forward(req, resp);
    }

    private void insertDepartment(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        Department department = new Department(name, city);
        departmentDao.create(department);
        resp.sendRedirect("list");
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws
            IOException, SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        Department department = new Department(id);
        departmentDao.delete(department);
        resp.sendRedirect("list");
    }

}
