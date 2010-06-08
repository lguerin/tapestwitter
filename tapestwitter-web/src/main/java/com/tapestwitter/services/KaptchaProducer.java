package com.tapestwitter.services;

import com.google.code.kaptcha.Producer;

/**
 * Extension of KatpchaProducer that exposes the images width and height (in
 * pixels).
 * 
 */
public interface KaptchaProducer extends Producer
{

	int getWidth();

	int getHeight();
}
