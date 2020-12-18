package com.equinix.appops.dart.portal.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;

import com.equinix.appops.dart.portal.constant.CAPACITYConstants;
import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.Role;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.model.domain.EnumResourceType;
import com.equinix.appops.dart.portal.model.domain.RoleComparator;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.UserService;
/**
 * 
 * @author MM
 *
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter{
	
	private Hashtable<String,String> ldapContext = new Hashtable<String,String>();
	private static final Logger log = LoggerFactory.getLogger("requestFilterLogger");
	private AntPathMatcher matcher = new AntPathMatcher();
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private UserService userService;
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		String requestURL = httpServletRequest.getRequestURI();
		
		if(null!=requestURL && (!requestURL.contains("AppOpsDartPortal")||!requestURL.contains("10.193.164.145:8080"))){
		
		/*System.out.println("requeustURL: "+requestURL);
		if(requestURL.contains("index")){
			System.out.println("@@@@@@@@@@@\nYes, index.html loaded...\n@@@@@@@@@@");
		}*/
		log.debug("URL# "+requestURL);
		log.debug("AuthType# "+httpServletRequest.getAuthType());
		log.debug("LocalAddress# "+httpServletRequest.getLocalAddr());
		log.debug("Method# "+httpServletRequest.getMethod());
		log.debug("RemoteAddress# "+httpServletRequest.getRemoteAddr());
		if(matcher.match("/AppOpsDartPortalMS/restservice/v1.0/**", requestURL)){
			log.debug("Web service call");
			chain.doFilter(request, response);
			HttpSession webServiceSession=httpServletRequest.getSession();
			if(null!=webServiceSession && null==webServiceSession.getAttribute("user")){
				webServiceSession.invalidate();
				log.debug("Web service session terminated");
			}
		}else if (matcher.match("/AppOpsDartPortalMS/v1.0/**", requestURL) 
				|| matcher.match("/AppOpsDartPortalMS/js/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/css/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/images/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/img/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/*.ico", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/fonts/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/header.html", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/header*", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/popup.html", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/unauthorized.html", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/logout.html", requestURL)
				|| matcher.match("*.json", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/restservice/v1.0/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/dashboard**/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/assets/**", requestURL)
				|| matcher.match("/AppOpsDartPortalMS/home/logout", requestURL)) {
			
			chain.doFilter(request, response);

		} else if(httpServletRequest!=null && requestURL!=null 
				&& ByPassURL(httpServletRequest)){
			String ssoUser = null;
			String groupCN;
			int equalsIndex;
			int commaIndex;
			String groupName;
			String searchFilter;

			//String emfRemoteUser = "C000APLOGIX_REMOTE_USER";
			String emfRemoteUser = configService.getValueByKey(CAPACITYConstants.SSO_USER_HTTPHDR);
			User user = new User();

			Object userobj = httpServletRequest.getSession().getAttribute("user");
				if (userobj == null) {
					String dartAccessQueryString = configService.getValueByKey("DART_ACCESS_QUERY_STRING_USER");
					if (StringUtils.isNotEmpty(dartAccessQueryString) && "Y".equalsIgnoreCase(dartAccessQueryString)) {
						ssoUser = request.getParameter("userId");
					}
					log.info("User ID from Request ---> " + ssoUser + "; Query String DART Access ---> "+dartAccessQueryString);
					if (ssoUser == null || ssoUser.equals("")) {
						Enumeration names = httpServletRequest.getHeaderNames();
						while (names.hasMoreElements()) {
							String headerName = names.nextElement().toString();
							if (headerName.equalsIgnoreCase(emfRemoteUser)) {
								ssoUser = httpServletRequest.getHeader(headerName);

							}
						}
					}
					log.info("SSO User ---> " + ssoUser);
					if (ssoUser != null && (!ssoUser.isEmpty())) {
						try {
							UserInfo userInfo = userService.getUser(ssoUser);
							/*
							 * DirContext ctx = new
							 * InitialLdapContext(ldapContext, null); // Create
							 * the search controls
							 * 
							 * SearchControls searchCtls = new SearchControls();
							 * // Specify the attributes to return String
							 * returnedAtts[] = { "cn", "sn",
							 * "givenname","SAMAccountName", "mail", "memberOf",
							 * "c", "co", "distinguishedName",
							 * "extensionAttribute13", "physicaldeliveryaddress"
							 * };
							 * searchCtls.setReturningAttributes(returnedAtts);
							 * searchCtls.setSearchScope(SearchControls.
							 * SUBTREE_SCOPE); // specify the LDAP search
							 * filter. searchFilter =
							 * "(&(objectClass=person)(samaccountname=" +
							 * ssoUser + "))"; String searchBase =
							 * "DC=global,DC=equinix,DC=com"; NamingEnumeration
							 * answer = ctx.search(searchBase, searchFilter,
							 * searchCtls); SearchResult sr = (SearchResult)
							 * answer.next(); Attributes attrs =
							 * sr.getAttributes(); if
							 * (attrs.get("memberOf").size() > 0) {
							 * NamingEnumeration groupattr =
							 * attrs.get("memberOf") .getAll(); if (groupattr !=
							 * null) { try { while (groupattr.hasMore()) {
							 * groupCN = groupattr.next().toString();
							 * equalsIndex = groupCN.indexOf('=', 1); commaIndex
							 * = groupCN.indexOf(',', 1); groupName =
							 * groupCN.substring( (equalsIndex + 1),
							 * commaIndex); grpNameList.add(groupName); } }
							 * catch (Exception e) { log.
							 * error("Error in getting Group Names in request filter"
							 * + e.getMessage()); log.
							 * error("Error in getting Group Names in request filter:"
							 * ,e);
							 * 
							 * }
							 * 
							 * } NamingEnumeration allAttributes =
							 * attrs.getAll(); Map<String, String> usrmap = new
							 * HashMap<String, String>(); if (allAttributes !=
							 * null) { try { while (allAttributes.hasMore()) {
							 * Attribute attr = (Attribute) allAttributes
							 * .next(); String name = attr.getID(); Object value
							 * = attr.get(); usrmap.put(name, value.toString());
							 * } }
							 * 
							 * catch (Exception e) { log.
							 * error("Error in getting User attributes in request filter"
							 * + e.getMessage()); log.
							 * error("Error in getting User attributes in request filter"
							 * ,e);
							 * 
							 * } } populateUser(usrmap, user, ssoUser,
							 * grpNameList); userInfoLog(user); //Role role =
							 * getPirorityRole(httpServletRequest,user); try {
							 * 
							 * 
							 * //List<Resource> resources =
							 * resourceService.getResourceByRole(role);
							 * //role.setResources(new
							 * HashSet<Resource>(resources));
							 * 
							 * } catch (Exception ex) { log.
							 * debug("Error with fetching user's resources: ",
							 * ex); } //user.setRole(role);
							 * 
							 * ctx.close(); log.debug("User adding "+user);
							 * httpServletRequest.getSession().setAttribute(
							 * "user",user);
							 * log.debug("User added "+user.getFirstName()); }
							 */
							populateUser(user, userInfo);
							// userInfoLog(user);
							Role role = getPirorityRole(httpServletRequest, user, userInfo);
							try {
								// List<Resource> resources =
								// resourceService.getResourceByRole(role);
								List<DartResource> resources = user.getRole().getDartResources();
							} catch (Exception ex) {
								log.debug("Error with fetching user's resources: ", ex);
							}
							user.setRole(role);
						} catch (Exception e) {
							log.error("Problem searching Directory ", e);

						}
					} else {

						log.error("User Object in Request is null");
					}
				}
			HttpSession localSession = httpServletRequest.getSession();
			log.info("Local Session ---> " + localSession);
			User localUser = (User)localSession.getAttribute("user");
			log.info("Local User ---> " + localUser);
			if(null!=localUser && isAuthorize(httpServletRequest, requestURL)){
				chain.doFilter(request, response);
			}else if(null!=localUser && isAuthorize(httpServletRequest, requestURL)==false){
				localUser.setUnAuthorized(true);
				httpServletRequest.getSession().setAttribute("user",localUser);
				localSession.invalidate();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("/AppOpsDartPortalMS/unauthorized.html");
				return;
			}else{
				localSession.invalidate();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				if (requestURL.contains("logout")) {
					httpResponse.sendRedirect("/AppOpsDartPortalMS/logout.html");
					return;
				} else {
					httpResponse.sendRedirect("/AppOpsDartPortalMS/unauthorized.html");
					return;
				}
			}
		}else{
			boolean permission = false;
			permission=isAuthorize(httpServletRequest, requestURL);
			if (permission) {
			chain.doFilter(request, response);
			} else {
				log.debug("You don't have access on: "+requestURL);
				((HttpServletResponse) response).sendRedirect("/AppOpsDartPortalMS/unauthorized.html");
				return;
			}
		}
	}
	}

	private void populateUser(User user, UserInfo userInfo) {
		user.setUserId(userInfo.getUserId());
		
		user.setUserInfo(userInfo);
		
		List<String> groups = new ArrayList<String>();
		userInfo.getUserGroups().forEach(userGroup ->{
			groups.add(userGroup.getGroup().getGroupName());
			if(null==user.getPrimaryAssignGroup() && userGroup.getPrimaryGroup()==1){
				user.setPrimaryAssignGroup(userGroup.getGroup());
			}
		});
		
		user.setUsrGroups(groups);
		user.setFirstName(userInfo.getFirstName());
		user.setLastName(userInfo.getLastName());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
	private boolean ByPassURL(HttpServletRequest httpServletRequest) {
		//String byPassURL = "index,SqlId,emailalertaudit,news-template,new-world-map-latest";
		String byPassURL = configService.getValueByKey("SSO_BY_PASS_URL");
		for (String url : byPassURL.split(",")) {
			if(httpServletRequest.getRequestURI().contains(url)){
				return true;
			}
		}
		
		
		return false;
	}

	private boolean isAuthorize(HttpServletRequest httpServletRequest,String requestURL) {
		Boolean permission = false;
		User user = httpServletRequest.getSession().getAttribute("user") == null ? null : (User) httpServletRequest.getSession().getAttribute("user");
		
		if(user!=null && user.getRole() !=null){
			/********* BY PASS ACCORDING TO ID Start*******/
			if(user.getUserId().contains("vcv") || user.getUserId().contains("kpunyakoteeswaran")) {
				return true;
			}
			/********* BY PASS ACCORDING TO ID close*******/
			Role role = user.getRole();
			Set<DartResource> resources = new HashSet<DartResource>(role.getDartResources());
			for (DartResource resource : resources) {
				log.debug("ResourceType:"+resource.getResourceType() + " # resourceName:" + resource.getResourceName());
				if (resource.getResourceType().trim().equals(EnumResourceType.URL.toString())) {
					if(requestURL.contains(resource.getResourceName())|| matcher.match(resource.getResourceName(), requestURL)){
						permission= true;
						break;
					}
				}
			}
		}
		//return permission;
		
		return true;
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		populateLDAPContextAttributes();
	}
	
	

	
	private void populateLDAPContextAttributes() {

		
		/*
		 * String ldapAdminName =
		 * configService.getValueByKey(CAPACITYConstants.LDAP_ADMIN_NAME); String
		 * ldapPassword = configService.getValueByKey(CAPACITYConstants.LDAP_PASSWORD);
		 * String ldapURL = configService.getValueByKey(CAPACITYConstants.LDAP_URL);
		 * String securityAuth =
		 * configService.getValueByKey(CAPACITYConstants.LDAP_SECURITY_AUTH); String
		 * initContextFactory = configService
		 * .getValueByKey(CAPACITYConstants.LDAP_INITIAL_CONTEXT);
		 * 
		 * 
		 * ldapContext.put(Context.INITIAL_CONTEXT_FACTORY, initContextFactory);
		 * ldapContext.put(Context.SECURITY_AUTHENTICATION, securityAuth);
		 * ldapContext.put(Context.SECURITY_PRINCIPAL, ldapAdminName);
		 * ldapContext.put(Context.SECURITY_CREDENTIALS, ldapPassword);
		 * ldapContext.put(Context.PROVIDER_URL, ldapURL);
		 * 
		 */
		
		/*
		 * ldapContext.put(Context.INITIAL_CONTEXT_FACTORY,
		 * "com.sun.jndi.ldap.LdapCtxFactory");
		 * ldapContext.put(Context.SECURITY_AUTHENTICATION, "simple");
		 * ldapContext.put(Context.SECURITY_PRINCIPAL,
		 * "CN=tibco-admin,OU=Service,OU=Users,OU=Equinix,DC=global,DC=equinix,DC=com");
		 * ldapContext.put(Context.SECURITY_CREDENTIALS, "L7RnOdqlYmW");
		 * ldapContext.put(Context.PROVIDER_URL, "ldap://global.equinix.com:389");
		 */
		 

	}
	
	private void populateUser(Map<String, String> usrmap, User user,
			String ssoUser, List<Long> grpNameList) {


		for (Entry<String, String> entry : usrmap.entrySet()) {
			if (entry.getKey().equals("givenName")) {
				user.setFirstName(entry.getValue());
			}
			if (entry.getKey().equals("sn")) {
				user.setLastName(entry.getValue());
			}
			if (entry.getKey().equals("mail")) {
				user.setEmailId(entry.getValue());
			}
			if (entry.getKey().equals("co")) {
				user.setCountry(entry.getValue());
				//user.setRegion(ADUtility.getRegionFromCountry(entry.getValue()));
			}
			if (entry.getKey().equals("cn")) {
				user.setFullname(entry.getValue());
			}
			/*if (entry.getKey().equals("c")) {
				user.setRegion(entry.getValue());
			}*/
			if (entry.getKey().equals("SAMAccountName")) {
				user.setUserId(entry.getValue());
			}
			/*
			 * if(null!=grpNameList && !grpNameList.isEmpty()){
			 * user.setUsrGroups(grpNameList); }
			 */
			/*if (entry.getKey().equals("distinguishedName")) {
				if(entry.getValue().contains("OU=AMER")){
					user.setRegion("AMER");
				}else if(entry.getValue().contains("OU=APAC")){
					user.setRegion("APAC");
				}else if(entry.getValue().contains("OU=EMEA")){
					user.setRegion("EMEA");
				}
			}*/
			if (user.getUserId() == null || user.getUserId().equals("")) {
				user.setUserId(ssoUser);
			}
		}

	}
	
	private void userInfoLog(User user) {
		try {
			
			UserInfo userInfo = userService.getUser(user.getUserId());
			user.setUserInfo(userInfo);

		} catch (Exception e) {
			log.error("Error in populating User Permissions in request filter"
					, e);

		}

	}
	
	/*
	 * private Role getPirorityRole(HttpServletRequest request, User user) {
	 * List<Role> roles = new ArrayList<Role>(); Role userRole = null; List<Group>
	 * groups = securityBusinessProcess.getAllGroups(); for (Group group : groups) {
	 * List<String> userGroups = user.getUsrGroups(); if(userGroups==null){ break; }
	 * for (String userGroup : userGroups) {
	 * if(group.getGroupName().equals(userGroup)){ Role role = group.getRole(); if
	 * (role != null) { roles.add(group.getRole()); } } }
	 * 
	 * } if (roles.size() > 0) { Collections.sort(roles, new RoleComparator());
	 * log.info("RoleName: "+roles.get(0).getRoleName()); userRole=
	 * roleService.getRoleByName(roles.get(0).getRoleName()); } else { userRole=
	 * roleService.getRoleByName("DEFAULT"); } log.info("UserRole: "+userRole);
	 * initilizeSecurity(user,userRole,request); return userRole; }
	 */
	
	private Role getPirorityRole(HttpServletRequest request, User user, UserInfo userInfo) {
	/*	List<Role> roles = new ArrayList<Role>(userInfo.getRoles());
		Role userRole = null;
		if (roles.size() > 0) {
			Collections.sort(roles, new RoleComparator());
			log.info("RoleName: "+roles.get(0).getRoleName());
			userRole= roles.get(0);
		} else {
			userRole= roles.get(0);
		}
		log.info("UserRole: "+userRole);
		userInfo.getPrimaryRole()
		initilizeSecurity(user,userRole,request);
		return userRole;
		*/
		Role userRole  = userInfo.getPrimaryRole();
		log.info("UserRole: "+userRole);
		initilizeSecurity(user,userRole,request);
		return userRole;
	}
	
	
	private void initilizeSecurity(User user, Role role,HttpServletRequest request){
		user.setRole(role);
	    HttpSession session = request.getSession(true);	   
	    session.setAttribute("user", user);
	}

}
