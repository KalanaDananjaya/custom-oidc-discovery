package auth.server.info;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Keystore extends HttpServlet {

    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type as 'application/json'
        response.setContentType("application/json");
        JSONObject outputContent = DiscoveryDataHolder.getInstance().getKeystoreData();

        // Get the PrintWriter object from response to write the required json object to the output stream.
        PrintWriter out = response.getWriter();
        out.print(outputContent);
        out.flush();
    }

    public void destroy() {
        // do nothing.
    }
}
