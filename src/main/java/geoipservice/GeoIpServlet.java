package geoipservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author oker <zsolt@takacs.cc>
 */
public class GeoIpServlet extends HttpServlet {
    public GeoIpServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String country = new GeoIpCountryCommand(req.getParameter("ip")).execute();

        PrintWriter pw = resp.getWriter();
        pw.write(country);
    }
}
