package com.ricex.aft.servlet.config;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
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
import com.ricex.aft.servlet.controller.view.DeviceViewController;
import com.ricex.aft.servlet.controller.view.HomeController;
import com.ricex.aft.servlet.controller.view.RequestViewController;
import com.ricex.aft.servlet.gcm.GCMDeviceNotifier;
import com.ricex.aft.servlet.manager.DeviceManager;
import com.ricex.aft.servlet.manager.FileManager;
import com.ricex.aft.servlet.manager.RequestManager;
import com.ricex.aft.servlet.mapper.DeviceMapper;
import com.ricex.aft.servlet.mapper.FileMapper;
import com.ricex.aft.servlet.mapper.RequestMapper;
import com.ricex.aft.servlet.util.GsonFactory;

@Configuration
//@ComponentScan (basePackages = {"com.ricex.aft.servlet.controller"})
public class ApplicationConfig extends WebMvcConfigurationSupport  {

	
	@Bean
	public DeviceController deviceController() throws Exception {
		DeviceController deviceController = new DeviceController();
		deviceController.setDeviceManager(deviceManager());
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
	
	
	/* Old JSP View Resolver
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}*/
	
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
	
	/** The Device mapper to be used by the controllers
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
	
	/** The File mapper to be used by the controllers
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
		return requestManager;
	}
	
	@Bean
	public GCMDeviceNotifier gcmDeviceNotifier() {
		return new GCMDeviceNotifier();
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

	
}
