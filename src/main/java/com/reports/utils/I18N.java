package com.reports.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Convenient Internationalization utility class
 * 
 * @author Satya
 */
@Component
public class I18N {
    
    private MessageSource messageSource;

    @Autowired
    I18N(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	/**
	 * Try to resolve the message, with the current bound Locale.
     * 
	 * @param label the code to lookup up, such as 'calculator.noRateSet'
	 * @param args an array of arguments that will be filled in for params within
	 * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 * or {@code null} if none.
	 * @return the resolved message
	 * @see java.text.MessageFormat
	 */    
    public String getMessage(String label, @Nullable Object[] args) {
        // Thread-bound ContextHolder, hence why it must be read every time
        Locale locale = LocaleContextHolder.getLocale();

        return this.getMessage(label, args, locale);
    }

	/**
	 * Try to resolve the message.
	 * @param label the code to lookup up, such as 'calculator.noRateSet'
	 * @param args an array of arguments that will be filled in for params within
	 * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 * or {@code null} if none.
	 * @param locale the locale in which to do the lookup
	 * @return the resolved message
	 * @see java.text.MessageFormat
	 */
    public String getMessage(String label, @Nullable Object[] args, Locale locale) {
        return this.messageSource.getMessage(label, args, locale);
    }
   
}