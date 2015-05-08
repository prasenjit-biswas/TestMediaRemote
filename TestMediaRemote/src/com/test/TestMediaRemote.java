package com.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.business.MainRemote;
import com.business.RequestHandler;
import com.business.V7admin2;
import com.business.library_pull;
import com.business.tp_requestHandler;
import com.business.tp_utils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(tp_utils.class)
public class TestMediaRemote {
	
	tp_requestHandler theHandlerMock;
	RequestHandler theRequestMock;
    V7admin2 theServletMock;	
	@Before
	public void setUp() throws Exception{
		theHandlerMock = mock(tp_requestHandler.class);
		theRequestMock = mock(RequestHandler.class);
		theServletMock = mock(V7admin2.class);
		theHandlerMock.theRequest = theRequestMock;
		theHandlerMock.theServlet = theServletMock;
	}
	
	
    @Test
    public void TestGetMediaFromRemote1() throws Exception{
      MainRemote mainRemoteStub = Mockito.spy(new MainRemote());
	  String testid ="123";
	  String remoteTestId ="1234";
	  String dataUrlStr= "RemoteServer" + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + "321"+"&mediaName="+"abc.jpg";
	  Map<String, String> mediaMap = new HashMap<String, String>();
	  when(theHandlerMock.getParam(library_pull.REMOTE_SERVER)).thenReturn("RemoteServer");
	  when(theHandlerMock.theRequest.getHeader("Authorization")).thenReturn("Authorization");
	  mainRemoteStub.getMediaFromRemote(theHandlerMock, mediaMap, testid, remoteTestId);
	  verify(mainRemoteStub, times(0)).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
    }
    
    @Test
    public void TestGetMediaFromRemote2() throws Exception{
	  MainRemote mainRemoteStub = Mockito.spy(new MainRemote());
	  String testid ="123";
	  String remoteTestId ="1234";
	  String dataUrlStr= "RemoteServer" + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + "321"+"&mediaName="+"abc.jpg";
	  Map<String, String> mediaMap = new HashMap<String, String>();
	  mediaMap.put("abc.jpg", "321_50_50");
	  when(theHandlerMock.getParam(library_pull.REMOTE_SERVER)).thenReturn("RemoteServer");
	  when(theHandlerMock.theRequest.getHeader("Authorization")).thenReturn("Authorization");
	  doNothing().when(mainRemoteStub).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
	  mainRemoteStub.getMediaFromRemote(theHandlerMock, mediaMap, testid, remoteTestId);
	  verify(mainRemoteStub, times(0)).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
    }
    
    @Test
    public void TestGetMediaFromRemote3() throws Exception{
	  MainRemote mainRemoteStub = Mockito.spy(new MainRemote());
	  String testid ="123";
	  String remoteTestId ="1234";
	  String dataUrlStr= "RemoteServer" + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + "321"+"&mediaName="+"abc.jpg";
	  Map<String, String> mediaMap = new HashMap<String, String>();
	  mediaMap.put("abc.jpg", "123_321_50_50");
	  when(theHandlerMock.getParam(library_pull.REMOTE_SERVER)).thenReturn("RemoteServer");
	  when(theHandlerMock.theRequest.getHeader("Authorization")).thenReturn("Authorization");
	  doNothing().when(mainRemoteStub).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
	  mainRemoteStub.getMediaFromRemote(theHandlerMock, mediaMap, testid, remoteTestId);
	  verify(mainRemoteStub, times(1)).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
    }
    
    /*@Test
    public void TestGetMediaFromRemote3() throws Exception{
	  MainRemote mainRemoteStub = Mockito.spy(new MainRemote());
	  tp_utils tp_utilsStub = mock(tp_utils.class); 
	  StringBuilder messageText = new StringBuilder("")
      .append("\n Remote media ID :"+"321")
      .append("\n Host media ID :" +"123")
      .append("\n Media Name : "+"abc.jpg")
      .append("\n Host TestID : "+"123")
      .append("\n Remote TestID : "+"1234")
      .append("\n Remote server : "+"RemoteServer")
      .append("\n Host server : "+"http://cloudurl");
	  String testid ="123";
	  String remoteTestId ="1234";
	  theHandlerMock.baseServer="http://cloudurl/";
	  theHandlerMock.theServlet.adminEmail = "biswas.prasenjit@tcs.com";
	  String dataUrlStr= "RemoteServer" + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + "321"+"&mediaName="+"abc.jpg";
	  Map<String, String> mediaMap = new HashMap<String, String>();
	  mediaMap.put("abc.jpg", "123_321_50_50");
	  when(theHandlerMock.getParam(library_pull.REMOTE_SERVER)).thenReturn("RemoteServer");
	  when(theHandlerMock.theRequest.getHeader("Authorization")).thenReturn("Authorization");
	  doThrow(new Exception()).when(mainRemoteStub).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
	  //doNothing().when(tp_utilsStub).sendMail(theHandlerMock, "donotreply@cloudurl", "biswas.prasenjit@tcs.com", "Unable to pull Media from Remote server", messageText.toString());
	  mainRemoteStub.getMediaFromRemote(theHandlerMock, mediaMap, testid, remoteTestId);
	  verify(mainRemoteStub, times(3) ).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
    }*/
    
    @Test
    public void TestGetMediaFromRemote4() throws Exception{
	  MainRemote mainRemoteStub = Mockito.spy(new MainRemote());
	  PowerMockito.spy(tp_utils.class);
	  StringBuilder messageText = new StringBuilder("")
      .append("\n Remote media ID :"+"321")
      .append("\n Host media ID :" +"123")
      .append("\n Media Name : "+"abc.jpg")
      .append("\n Host TestID : "+"123")
      .append("\n Remote TestID : "+"1234")
      .append("\n Remote server : "+"RemoteServer")
      .append("\n Host server : "+"http://cloudurl");
	  String testid ="123";
	  String remoteTestId ="1234";
	  theHandlerMock.baseServer="http://cloudurl/";
	  theHandlerMock.theServlet.adminEmail = "biswas.prasenjit@tcs.com";
	  String dataUrlStr= "RemoteServer" + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + "321"+"&mediaName="+"abc.jpg";
	  Map<String, String> mediaMap = new HashMap<String, String>();
	  mediaMap.put("abc.jpg", "123_321_50_50");
	  when(theHandlerMock.getParam(library_pull.REMOTE_SERVER)).thenReturn("RemoteServer");
	  when(theHandlerMock.theRequest.getHeader("Authorization")).thenReturn("Authorization");
	  doThrow(new Exception()).when(mainRemoteStub).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
	  //Mockito.when(tp_utils.sendMail(theHandlerMock, "donotreply@cloudurl", "biswas.prasenjit@tcs.com", "Unable to pull Media from Remote server", messageText.toString())).thenReturn(false);
	  PowerMockito.doNothing().when(tp_utils.class);
	  tp_utils.sendMail(theHandlerMock, "donotreply@cloudurl", "biswas.prasenjit@tcs.com", "Unable to pull Media from Remote server", messageText.toString());
	  try{
		  mainRemoteStub.getMediaFromRemote(theHandlerMock, mediaMap, testid, remoteTestId);
	  }catch(Exception ex){
		  System.out.println(" my Exception message : "+ex.getMessage());
	  }
	  verify(mainRemoteStub, times(3)).callRemoteMedia(theHandlerMock, "Authorization", dataUrlStr, "123", "50", "50");
    }
}
