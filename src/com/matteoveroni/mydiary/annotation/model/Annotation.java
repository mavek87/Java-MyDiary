package com.matteoveroni.mydiary.annotation.model;

import java.util.Date;

/**
 *
 * @author Matteo Veroni
 */
public interface Annotation {

    public long getId();

    public void setId(long id);

    public String getTitle();

    public void setTitle(String title);

    public String getMessage();

    public void setMessage(String message);

    public String getAuthor();

    public void setAuthor(String author);

    public Date getCreationDate();

    public void setCreationDate(Date creationDate);
	
	public Date getLastModifationTimestamp();
	
	public void setLastModificationTimestamp(Date lastModificationDate);
}
