package MyServlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by TheKarlBrown on 6/23/15.
 */
public class HelloWorld extends HttpServlet {
    private PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
        out.println("<html>");
        htmlHead(out,new String[]{},"HelloWorld");
        out.println("Hello, this is a servlet");

        out.println("</html>");
    }

    public static void htmlHead(PrintWriter printWriter,String[] cssLinks,String title){
        printWriter.println("<head>");

        // Set the title
        printWriter.println("<title>"+ title + "</title>");

        // Normalize the CSS
        printWriter.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://raw.githubusercontent.com/necolas/normalize.css/master/normalize.css\" >");

        // Add any additional stylesheets necessary
        for (String cssLink : cssLinks){
            printWriter.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ cssLink + "\" >");
        }

        printWriter.println("</head>");

    }
}
