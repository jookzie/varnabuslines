package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@Entity
@Getter
public class Alert
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Date timestamp;

    public Alert()
    {
    }

    public Alert(final String title, final String content, final Date timestamp)
    {
        setTitle(title);
        setContent(content);
        setTimestamp(timestamp);
    }

    public void setTimestamp(final Date timestamp)
    {
        if(isNullOrEmpty(timestamp))
            throw new ValidationException("Timestamp cannot be null or empty.");

        if(timestamp.after(new Date()))
            throw new ValidationException("Timestamp cannot be in the future.");

        this.timestamp = timestamp;
    }


    public void setContent(final String content)
    {
        if(isNullOrEmpty(content))
            throw new ValidationException("Content cannot be null or empty.");

        this.content = content;
    }

    public void setTitle(final String title)
    {
        if(isNullOrEmpty(title))
            throw new ValidationException("Title cannot be null or empty.");

        this.title = title;
    }

    public void setId(final long id)
    {
        if(id < 0)
            throw new ValidationException("Id cannot be a negative number.");
        this.id = id;
    }
}
