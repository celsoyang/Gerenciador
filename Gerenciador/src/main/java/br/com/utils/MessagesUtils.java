package br.com.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesUtils {

	public static void errorMessage(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
	}

	public static void waringMessage(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, "", msg));
	}

	public static void infoMessage(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
	}

}
