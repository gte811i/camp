package avalon.cmd;
//import java.io.IOException;
//
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlButton;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
//import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
//
///**
// * 
// */
//
///**
// * @author Shane
// *
// */
//public class Login {
//
//	public static void main(String args[])
//	{
//		try(WebClient webClient = new WebClient(BrowserVersion.CHROME);)
//		{
//			webClient.getOptions().setAppletEnabled(true);
//			webClient.getOptions().setRedirectEnabled(true);
//			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//			webClient.getOptions().setThrowExceptionOnScriptError(true);
//			webClient.getOptions().setJavaScriptEnabled(true);
//			webClient.getOptions().setActiveXNative(true);
//			try {
//				final HtmlPage page1 = webClient.getPage("https://insight.cmdgroup.com");
//				//		System.out.println(page1.asText());
//				//		final HtmlForm form = page1.getFormByName("login_form");
//				HtmlTextInput userName = page1.getHtmlElementById("txtUserName");
//				HtmlPasswordInput password = page1.getHtmlElementById("txtPassword");
//				HtmlButton logon = page1.getHtmlElementById("btnLogon");
//				password.removeAttribute("disabled");
//				userName.removeAttribute("disabled");
//				userName.setValueAttribute("gte811i@hotmail.com");
//				password.setValueAttribute("lamas123");
//
//				System.out.println("Before Click");
//				webClient.waitForBackgroundJavaScriptStartingBefore(1000);
//				System.out.println("Clicking....");
//				final HtmlPage logged = logon.click();
//				webClient.waitForBackgroundJavaScriptStartingBefore(1000);
//				System.out.println(logged.asText());
//				System.out.println("Finished");
//			} catch (FailingHttpStatusCodeException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//}
