package com.technovators.braillevoice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.Message;

public class SitesXmlPullParser {

	static final String KEY_ITEM = "item"; //site
	static final String KEY_TITLE = "title";//name
	static final String KEY_LINK = "link";//link
	static final String KEY_DESCRIPTION = "description";//about
	static final String KEY_PUBDATE= "pubDate"; 
	static final String KEY_IMAGE_URL = "image";
	static final String KEY_CHANNEL = "channel";
	public static List<StackSite> getStackSitesFromFile(Context ctx) {

		// List of StackSites that we will return
		List<StackSite> stackSites; //List<Message> messages
		stackSites = new ArrayList<StackSite>();
		
		// temp holder for current StackSite while parsing
		//StackSite curStackSite = null;//Message currentMessage
		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();//xpp is parser

			
			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("StackSites.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			// point the parser to our file.
			xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();
			StackSite curStackSite = null;
			//Message currentMessage = null;
            boolean done = false;

			// Loop through pull events until we reach END_DOCUMENT
			while (eventType != XmlPullParser.END_DOCUMENT &&!done) {
				// Get the current tag
				String name=null;
			//	String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
                    stackSites = new ArrayList<StackSite>();
                    break;
				case XmlPullParser.START_TAG:
					name= xpp.getName();
					if (name.equalsIgnoreCase(KEY_ITEM)) {
						// If we are starting a new <item> block we need
						//a new StackSite object to represent it
						curStackSite = new StackSite();
					}else if(curStackSite!=null){
						 if (name.equalsIgnoreCase(KEY_TITLE)) {
							// if </name> use setName() on curSite
							curStackSite.setTitle(xpp.nextText());
						} else if (name.equalsIgnoreCase(KEY_LINK)) {
							// if </link> use setLink() on curSite
							curStackSite.setLink(xpp.nextText());
						} else if (name.equalsIgnoreCase(KEY_DESCRIPTION)) {
							// if </about> use setAbout() on curSite
							curStackSite.setDescription(xpp.nextText());
						}else if (name.equalsIgnoreCase(KEY_PUBDATE)) {
							// if </image> use setImgUrl() on curSite
							curStackSite.setPubDate(xpp.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					 name = xpp.getName();
					 if (name.equalsIgnoreCase(KEY_ITEM) && curStackSite != null){
                         stackSites.add(curStackSite);
                     } else if (name.equalsIgnoreCase(KEY_CHANNEL)){
                         done = true;
                     }
                     break;
				
				}
				//move on to next iteration
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the populated list.
		return stackSites;
	}
}
