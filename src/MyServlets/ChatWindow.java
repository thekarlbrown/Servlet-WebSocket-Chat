package MyServlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by TheKarlBrown on 6/24/15.
 */
public class ChatWindow extends HttpServlet {
    private PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
        out.println("<html>");
        htmlHead(out,new String[]{"/chatWindow.css"},"Chat Window");
        chatCore(out,"www.thekarlbrown.com");

        out.println("</html>");
    }

    /**
     * Print out the HTML head including title and css content
     * @param printWriter PrintWriter to use when calling the method
     * @param cssLinks Any CSS to be included
     * @param title Title of the site
     */
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

    /**
     * Core HTML for the Chat Window
     * @param printWriter PrintWriter to use when calling the method
     * @param exitLink Link to direct user to upon closing chat
     */
    public static void chatCore (PrintWriter printWriter, String exitLink){
        printWriter.println("<div id=\"backdrop\"> ");
        printWriter.println("<div id=\"menu\">");
        printWriter.println("<p class=\"welcome\"> Welcome, <b></b> </p>");
        printWriter.println("<p class=\"exit\"><a id=\"exit\" href=\"" + exitLink + "\">Quit Chat</a></p>");
        printWriter.println("</div>");
        printWriter.println("<div id=\"chatBox\"> </div>");
        printWriter.println("<div id=\"userList\"> </div>");
        printWriter.println("<br style=\"clear:both;\"/>");
        printWriter.println("<form name=\"message\" action=\"\">");
        printWriter.println("<input name=\"usermsg\" type=\"text\" id=\"usermsg\" size=\"100\"/>");
        printWriter.println("<input name=\"sendmsg\" type=\"submit\" id=\"sendmsg\" value=\"Send\"/>");
        printWriter.println("</form>");
        printWriter.println("</div>");
    }
}
