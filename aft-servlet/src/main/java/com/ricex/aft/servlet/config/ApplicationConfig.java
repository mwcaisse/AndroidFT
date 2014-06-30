package com.ricex.aft.servlet.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.gson.Gson;
import com.ricex.aft.servlet.manager.DeviceManager;
import com.ricex.aft.servlet.manager.FileManager;
import com.ricex.aft.servlet.manager.RequestManager;
import com.ricex.aft.servlet.mapper.DeviceMapper;
import com.ricex.aft.servlet.mapper.FileMapper;
import com.ricex.aft.servlet.mapper.RequestMapper;
import com.ricex.aft.servlet.util.GsonFactory;

@Configuration
@ComponentScan (basePackages = {"com.ricex.aft.servlet.controller"})
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {

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
	
	@Bean
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
		return requestManager;
	}
	
	/** The multipart resolver bean
	 * 
	 * @return The multipart resolver bean
	 */
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	
}
