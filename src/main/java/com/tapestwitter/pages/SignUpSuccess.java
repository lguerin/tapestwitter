package com.tapestwitter.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;

/**
 * Page de confirmation
 * 
 * @author karesti
 *
 */
public class SignUpSuccess
{
	@Persist(PersistenceConstants.FLASH)
	private String email;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}
