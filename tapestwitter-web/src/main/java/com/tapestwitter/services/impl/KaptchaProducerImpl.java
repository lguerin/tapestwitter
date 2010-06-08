package com.tapestwitter.services.impl;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.tapestwitter.services.KaptchaProducer;

public class KaptchaProducerImpl implements KaptchaProducer
{

	private final DefaultKaptcha producer;

	private final int height;

	private final int width;

	public KaptchaProducerImpl(Map<String, String> configuration)
	{
		producer = new DefaultKaptcha();

		Config config = new Config(toProperties(configuration));

		producer.setConfig(config);

		height = config.getHeight();
		width = config.getWidth();
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public BufferedImage createImage(String text)
	{
		return producer.createImage(text);
	}

	public String createText()
	{
		return producer.createText();
	}

	private static Properties toProperties(Map<String, String> map)
	{

		Properties result = new Properties();

		for (String key : map.keySet())
		{
			result.put(key, map.get(key));
		}

		return result;

	}
}
