//package com.justchatapp.bean;
//
//import java.io.Serializable;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.wink.client.Resource;
//import org.apache.wink.client.RestClient;
//
//import com.google.gson.Gson;
//
//import com.justchatapp.vm.UserViewModel;
//
//@ViewScoped
//@ManagedBean(name = "profileBean")
//public class ProfileBean implements Serializable {
//	private String path = "http://130.237.84.211:8080/Faceoogle2/rest/";
//	private static final long serialVersionUID = 1L;
//	private String paramUser = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
//			.getRequest()).getParameter("user");
//	private UserViewModel vm;
//
//	public UserViewModel getVm() {
//		RestClient client = new RestClient();
//		Resource res = client.resource(path + "user/userinfo?user=" + paramUser);
//		String jsonNames = res.accept("application/json").get(String.class);
//		Gson gson = new Gson();
//		vm = gson.fromJson(jsonNames, UserViewModel.class);
//		return vm;
//	}
//
//	public void setVm(UserViewModel vm) {
//		this.vm = vm;
//	}
//
//	public String getParamUser() {
//		return paramUser;
//	}
//}
