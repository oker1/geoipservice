package geoipservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author oker <zsolt@takacs.cc>
 */
public class GeoIpServlet extends HttpServlet {
    public GeoIpServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Future<String> country = new CountryLookupCollapser(req.getParameter("ip")).queue();

        PrintWriter pw = resp.getWriter();
        try {
            pw.write(country.get());
        } catch (InterruptedException e) {
            throw new ServletException(e);
        } catch (ExecutionException e) {
            throw new ServletException(e);
        }
    }
}
