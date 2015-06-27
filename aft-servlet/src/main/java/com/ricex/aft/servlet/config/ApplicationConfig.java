package com.ricex.aft.servlet.config;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ricex.aft.servlet.controller.api.DeviceController;
import com.ricex.aft.servlet.controller.api.FileController;
import com.ricex.aft.servlet.controller.api.RequestController;
import com.ricex.aft.servlet.controller.api.UserController;
import com.ricex.aft.servlet.controller.view.DeviceViewController;
import com.ricex.aft.servlet.controller.view.HomeController;
import com.ricex.aft.servlet.controller.view.LoginViewController;
import com.ricex.aft.servlet.controller.view.RequestViewController;
import com.ricex.aft.servlet.gcm.GCMDeviceNotifier;
import com.ricex.aft.servlet.manager.DeviceImageManager;
import com.ricex.aft.servlet.manager.DeviceManager;
import com.ricex.aft.servlet.manager.FileManager;
import com.ricex.aft.servlet.manager.RegistrationKeyManager;
import com.ricex.aft.servlet.manager.RequestManager;
import com.ricex.aft.servlet.manager.UserAuthenticationTokenManager;
import com.ricex.aft.servlet.manager.UserManager;
import com.ricex.aft.servlet.mapper.DeviceImageMapper;
import com.ricex.aft.servlet.mapper.DeviceMapper;
import com.ricex.aft.servlet.mapper.FileMapper;
import com.ricex.aft.servlet.mapper.RegistrationKeyMapper;
import com.ricex.aft.servlet.mapper.RequestMapper;
import com.ricex.aft.servlet.mapper.UserAuthenticationTokenMapper;
import com.ricex.aft.servlet.mapper.UserMapper;
import com.ricex.aft.servlet.util.GsonFactory;

@Configuration
//@ComponentScan (basePackages = {"com.ricex.aft.servlet.controller"})

public class ApplicationConfig extends WebMvcConfigurationSupport  {	
	
	/** The security configuration for the app */
	@Autowired
	public SecurityConfig securityConfig;
	
	@Bean
	public DeviceController deviceController() throws Exception {
		DeviceController deviceController = new DeviceController();
		deviceController.setDeviceManager(deviceManager());
		deviceController.setDeviceImageManager(deviceImageManager());
		return deviceController;
	}
	
	@Bean
	public RequestController requestController() throws Exception {
		RequestController requestController = new RequestController();
		requestController.setDeviceManager(deviceManager());
		requestController.setRequestManager(requestManager());
		requestController.setDeviceNotifier(gcmDeviceNotifier());
		return requestController;
	}
	
	@Bean
	public FileController fileController() throws Exception{
		FileController fileController = new FileController();
		fileController.setFileManager(fileManager());
		return fileController;
	}
	
	@Bean 
	public UserController userController() throws Exception {
		UserController userController = new UserController();
		userController.setUserManager(userManager());
		userController.setUserAuthenticator(securityConfig.userAuthenticator());
		return userController;
	}
	
	/** Creates the Home Controller
	 * 
	 * @return The home controller
	 */
	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
	
	/** Creates the Device View Controller
	 * 
	 * @return The device view controller
	 */
	@Bean
	public DeviceViewController deviceViewController() {
		return new DeviceViewController();
	}
	
	/** Creates the Request View Controller
	 * 
	 * @return the request view controller
	 */
	@Bean
	public RequestViewController requestViewController() {
		return new RequestViewController();
	}
	
	/** Creates the Login View Controller
	 * 
	 * @return the login view controller
	 */
	@Bean
	public LoginViewController loginViewController() {
		return new LoginViewController();
	}
		
	/** Bean for the GSON Factory, to create the GSON Bean
	 * 
	 * @return The gson factory bean
	 */
	
	@Bean
	public GsonFactory gsonFactoryBean() {
		return new GsonFactory();
	}
	
	/** The GSON Bean, created with the gsonFactoryBean
	 * 
	 * @return the gson bean
	 */
	
	@Bean
	public Gson gsonBean() {
		return gsonFactoryBean().constructGson();
	}
	
	/** The SQL Session Factory to create new SQL Sessions
	 * 
	 * @return The sql session factory
	 * @throws Exception if the factory bean fails to create the sessionfactory
	 */
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();	
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		sqlSessionFactoryBean.setDataSource(dataSource());		
		return sqlSessionFactoryBean.getObject();		
	}
	
	/** The Data source for the sql session, is fetched using JNDI
	 * 
	 * @return The data source
	 */
	
	@Bean(destroyMethod="")
	public DataSource dataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/AFTDatabase");
		return dataSource;
	}
	
	/** The Device mapper to be used by the managers
	 * 
	 * @return THe device mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	
	@Bean
	public DeviceMapper deviceMapper() throws Exception {
		MapperFactoryBean<DeviceMapper> mapperFactoryBean = new MapperFactoryBean<DeviceMapper>();
		mapperFactoryBean.setMapperInterface(DeviceMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The Device Image mapper to be used by the managers
	 * 
	 * @return The device image mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	@Bean 
	public DeviceImageMapper deviceImageMapper() throws Exception {
		MapperFactoryBean<DeviceImageMapper> mapperFactoryBean = new MapperFactoryBean<DeviceImageMapper>();
		mapperFactoryBean.setMapperInterface(DeviceImageMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The File mapper to be used by the managers
	 * 
	 * @return The file mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	
	@Bean
	public FileMapper fileMapper() throws Exception {
		MapperFactoryBean<FileMapper> mapperFactoryBean = new MapperFactoryBean<FileMapper>();
		mapperFactoryBean.setMapperInterface(FileMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The User mapper to be used by the managers
	 * 
	 * @return The user mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	@Bean
	public UserMapper userMapper() throws Exception {
		MapperFactoryBean<UserMapper> mapperFactoryBean = new MapperFactoryBean<UserMapper>();
		mapperFactoryBean.setMapperInterface(UserMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The RegistrationKey Mapper
	 * 
	 * @return The user mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	@Bean
	public RegistrationKeyMapper registrationKeyMapper() throws Exception {
		MapperFactoryBean<RegistrationKeyMapper> mapperFactoryBean = new MapperFactoryBean<RegistrationKeyMapper>();
		mapperFactoryBean.setMapperInterface(RegistrationKeyMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The UserAuthenticationToken mapper
	 * 
	 * @return The user mapper
	 * @throws Exception If fetching the sql session factory failed
	 */	
	@Bean
	public UserAuthenticationTokenMapper userAuthenticationTokenMapper() throws Exception {
		MapperFactoryBean<UserAuthenticationTokenMapper> mapperFactoryBean = new MapperFactoryBean<UserAuthenticationTokenMapper>();
		mapperFactoryBean.setMapperInterface(UserAuthenticationTokenMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The Request Mapper to be used by the controllers
	 * 
	 * @return The request mapper
	 * @throws Exception If fetching the sql session factory failed
	 */
	
	@Bean
	public RequestMapper requestMapper() throws Exception {
		MapperFactoryBean<RequestMapper> mapperFactoryBean = new MapperFactoryBean<RequestMapper>();
		mapperFactoryBean.setMapperInterface(RequestMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	/** The bean for the device manager used by the controllers
	 * 
	 * @return The instance of the device manager
	 * @throws Exception if creating the DeviceMapper failed
	 */
	
	@Bean
	public DeviceManager deviceManager() throws Exception {
		DeviceManager deviceManager = DeviceManager.INSTANCE;
		deviceManager.setDeviceMapper(deviceMapper());
		return deviceManager;
	}
	
	/** The bean for the device image manager used by the controllers
	 * 
	 * @return The instance of the device image manager
	 * @throws Exception  If creating a mapper interface failed
	 */
	@Bean
	public DeviceImageManager deviceImageManager() throws Exception {
		DeviceImageManager deviceImageManager = DeviceImageManager.INSTANCE;
		deviceImageManager.setDeviceImageMapper(deviceImageMapper());
		deviceImageManager.setDeviceManager(deviceManager());
		return deviceImageManager;
	}
	
	/** The bean for the file manger used by the controllers
	 * 
	 * @return The instance of the file manager
	 * @throws Exception If created the File Mapper failed
	 */
	
	@Bean
	public FileManager fileManager() throws Exception {
		FileManager fileManager = FileManager.INSTANCE;
		fileManager.setFileMapper(fileMapper());
		return fileManager;
	}
	
	@Bean
	public UserManager userManager() throws Exception {
		UserManager userManager = UserManager.INSTANCE;
		userManager.setUserMapper(userMapper());
		userManager.setPasswordEncoder(passwordEncoder());
		userManager.setRegistrationKeyManager(registrationKeyManager());
		return userManager;
	}
	
	/** The bean for the request manager used by the controllers
	 * 
	 * @return The instance of the request manager
	 * @throws Exception if creating the RequestMapper failed
	 */
	
	@Bean
	public RequestManager requestManager() throws Exception {
		RequestManager requestManager = RequestManager.INSTANCE;
		requestManager.setRequestMapper(requestMapper());
		requestManager.setDeviceManager(deviceManager());
		requestManager.setFileManager(fileManager());
		requestManager.setUserManager(userManager());
		return requestManager;
	}
	
	/** The bean for the registration key manager
	 * 
	 * @return
	 * @throws Exception
	 */
	
	@Bean
	public RegistrationKeyManager registrationKeyManager() throws Exception {
		RegistrationKeyManager registrationKeyManager = RegistrationKeyManager.INSTANCE;
		registrationKeyManager.setRegistrationKeyMapper(registrationKeyMapper());
		return registrationKeyManager;
	}
	
	@Bean 	
	public UserAuthenticationTokenManager userAuthenticationTokenManager() throws Exception {
		UserAuthenticationTokenManager manager = UserAuthenticationTokenManager.INSTANCE;
		manager.setUserAuthenticationTokenMapper(userAuthenticationTokenMapper());
		manager.setUserManager(userManager());
		return manager;
	}
	
	@Bean
	public GCMDeviceNotifier gcmDeviceNotifier() throws Exception {
		return new GCMDeviceNotifier(gcmApiKey());
	}
	
	/** Looks up the API key for GCM from the servlet context
	 * 
	 * @return The gcm key
	 * @throws NamingException an error occured while fetching the key
	 */
	
	@Bean
	public String gcmApiKey() throws NamingException {
		JndiTemplate template = new JndiTemplate();
		return template.lookup("java:comp/env/AFT_GCM_API_KEY",String.class);
	}
	
	/** The multipart resolver bean
	 * 
	 * @return The multipart resolver bean
	 */
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/** Adds the custom Jackson 2 Http Message Converter, as well as the default message converters
	 * 	@param converters The list of add the message converters to
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.add(jacksonConverter());
		converters.add(gsonMessageConverter());
		addDefaultHttpMessageConverters(converters);
	};
	
	/** Creates the Jackson 2 Http Message converter to use.
	 * 	Enables Pretty Printing of JSON, and disables the fail on unrecognized properties	
	 * 
	 * @return
	 */
	public MappingJackson2HttpMessageConverter jacksonConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setPrettyPrint(true);
		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(om);
		return converter;
	}
	
	/** Creates the GSON message converter, to use Gson rather than Jackson for JSON serialization and deseiralization
	 * 
	 * @return The GSON message converter
	 */
	public GsonHttpMessageConverter gsonMessageConverter() {
		return new GsonHttpMessageConverter(gsonBean());
	}
	
	/** Add the resource handlers for js, css, etc
	 * 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry  registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}
	
	/** Creates the password encoder to use for Spring Security
	 * 
	 * @return The password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}