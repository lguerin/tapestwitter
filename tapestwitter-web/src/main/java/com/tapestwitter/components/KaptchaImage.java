package com.tapestwitter.components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.tapestwitter.services.KaptchaProducer;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

/**
 * Part of a Captcha based authentication scheme; a KaptchaImage generates a new
 * text image whenever it renders and can provide the previously
 * rendred text subsequently (it is stored persistently in the session).
 * 
 * The component renders an <img> tag, including width and height
 * attributes. Other attributes come from informal parameters.
 */
@SupportsInformalParameters
public class KaptchaImage
{

	@Persist
	private String captchaText;

	@Inject
	private KaptchaProducer producer;

	@Inject
	private ComponentResources resources;

	@Inject
	private Response response;

	public String getCaptchaText()
	{
		return captchaText;
	}

	void setupRender()
	{
		captchaText = producer.createText();
	}

	boolean beginRender(MarkupWriter writer)
	{
		Link link = resources.createEventLink("image");

		writer.element("img",

		"src", link.toAbsoluteURI(),

		"width", producer.getWidth(),

		"height", producer.getHeight());

		resources.renderInformalParameters(writer);

		writer.end();

		return false;
	}

	void onImage() throws IOException
	{

		BufferedImage image = producer.createImage(captchaText);

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");

		OutputStream stream = response.getOutputStream("image/jpeg");

		ImageIO.write(image, "jpg", stream);

		stream.flush();

		stream.close();
	}
}
