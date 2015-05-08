package com.business;

import java.util.Iterator;
import java.util.Map;

public class MainRemote {

		 
	//TCS modified code for PullContent
	 public void getMediaFromRemote(tp_requestHandler theHandler,Map<String,String> mediaMap, String testid, String remoteTestId) throws Exception{
      String server = theHandler.getParam(library_pull.REMOTE_SERVER);
	  String auth   = theHandler.theRequest.getHeader("Authorization");
	  Iterator<String> keyItr = null;
	  try{
		   if(mediaMap != null && !mediaMap.isEmpty()){
			   	keyItr = mediaMap.keySet().iterator();
			    String oldMediaId = null;
			    String newMediaID = null;
			    String height = "";
			    String width = "";
				     while(keyItr.hasNext()){
					      String mediaName = keyItr.next();
					      int retryCount = 0;
						      if(mediaName != null && !("").equals(mediaName)){
						       String newAndOldMediaId = mediaMap.get(mediaName);
						       if(newAndOldMediaId != null){
							        String[] arr = newAndOldMediaId.split("_");
							        if(arr != null && arr.length == 4){
								         if(arr[0] != null && !("").equals(arr[0])){
								          newMediaID = arr[0].trim();
								         }
								         if(arr[1] != null && !("").equals(arr[1])){
								          oldMediaId = arr[1].trim();
								         }
								         if(arr[2] != null && !("").equals(arr[2])){
								          width = arr[2].trim();
								         }
								         if(arr[3] != null && !("").equals(arr[3])){
								          height = arr[3].trim();
								         }
								         String dataUrlStr= server + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + oldMediaId+"&mediaName="+mediaName;
								         System.out.println(" dataUrlStr in getMediaFromRemote : "+dataUrlStr);
								         boolean mediaPulled = false;
								         //this code has been added to call media for Remote with retry count 2
								         while(retryCount <= 2){
									          try{
										           if(retryCount != 0){
										        	   System.out.println(" Trying to call Remote Media for times "+retryCount);
										           }
										           callRemoteMedia(theHandler, auth, dataUrlStr, newMediaID, width, height);
										           mediaPulled = true;
										           break;
									          }catch(Exception ex){
									        	  //System.out.println(" Exception in callRemoteMedia for testID : "+testid+" ,madiaName : "+mediaName+" ,new_old MediaIDs "+newAndOldMediaId);
									        	  //ex.printStackTrace();
									           retryCount++;
									          }
								         }
								         //this code has been added if the thread fails to add media from remote with retry count 2
								         //then this code will send mail to the dev groups. 
								         if(!mediaPulled){
								          String baseServer = theHandler.baseServer;
								          baseServer = baseServer.substring(baseServer.indexOf("//") + 2, baseServer.lastIndexOf("/"));
								          String from = "donotreply@" + baseServer;
								          String to = theHandler.theServlet.adminEmail;
								          StringBuilder subject = new StringBuilder("Unable to pull Media from Remote server");
								          StringBuilder messageText = new StringBuilder("")
								                 .append("\n Remote media ID :"+oldMediaId)
								                 .append("\n Host media ID :" +newMediaID)
								                 .append("\n Media Name : "+mediaName)
								                 .append("\n Host TestID : "+testid)
								                 .append("\n Remote TestID : "+remoteTestId)
								                 .append("\n Remote server : "+server)
								                 .append("\n Host server : "+"http://"+baseServer);
								          tp_utils.sendMail(theHandler, from, to, subject.toString(), messageText.toString());
								          throw new Exception(" Error in pulling media for mediaName : "+mediaName
								               + " , HostMediaId : " + newMediaID
								               + " , remoteMediaID : " + oldMediaId
								               + " , hostTestID : " + testid
								               + " , remoteTestId : "+remoteTestId);
								         }
							        }else{
							        	System.out.println(" getMediaFromRemote arr is NULL or length less than 2. newAndOldMediaId "+newAndOldMediaId);
							        }       
						       }else{
						    	   System.out.println(" getMediaFromRemote newAndOldMediaId is NULL or Empty.");
						       }
					      }else{
					    	  System.out.println(" getMediaFromRemote mediaName is NULL or Empty.");
					      }
				     }							
		   }
	  }catch(Exception e){
		  System.out.println(" Exception in getMediaFromRemote for mediaMap "+mediaMap);
		  e.printStackTrace();
	   throw new Exception(" An error has occurred while pulling media from remote.");
	  }
	 }

	 public void callRemoteMedia(tp_requestHandler theHandler, String auth, String dataUrlStr, String newMediaID, String width, String height) throws Exception {
	  String theResult = null;
	  //String dataUrlStr= server + "library.tpx?REQUEST=" + library_pull.PULL_REQUEST + "&" + library_pull.PULL_REQUEST + "=" + "media_info" + "&" + "media_info" + "=" + oldMediaId+"&mediaName="+mediaName;
	  //TCS modified for RMS,NON RMS pull functionality
	  //theResult = library_pull.getRemoteData(dataUrlStr,auth,null);
	  theResult = library_pull.getRemoteData(theHandler,dataUrlStr,auth,null);
	  throw new Exception();
	  //TCS modified for RMS,NON RMS pull functionality
	  //mediaInsert(theHandler, library_pull.testStreamFromString(theResult),newMediaID, width, height);
	 }
}
