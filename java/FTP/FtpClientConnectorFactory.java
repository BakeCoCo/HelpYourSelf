package com.flexfit.comm.dim.ftp;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;


public class FtpClientConnectorFactory implements FactoryBean<FtpClientConnector>{

	@Override
	public FtpClientConnector getObject() throws Exception {
		
		FtpClientConnector ftpClientConnector = new FtpClientConnector();
		
		// sftp 접속 정보로 사용 될 설정값을 등록합니다.
		ftpClientConnector.setConnType(storageType);
		ftpClientConnector.setConnHost(sftpHost);
		ftpClientConnector.setConnPort(Integer.parseInt(sftpPort));
		ftpClientConnector.setConnUsername(sftpUsername);
		ftpClientConnector.setConnPassword(sftpPassword);
		ftpClientConnector.setRootDirectory(rootDirectory);
		ftpClientConnector.setConnMode(sftpMode);
		
		return ftpClientConnector;
	}

	@Override
	public Class<?> getObjectType() {
		return FtpClientConnector.class;
	}
	
	@Value("${file.upload.type}")
	private String storageType;

	@Value("${file.upload.sftp.host}")
	private String sftpHost;
	
	@Value("${file.upload.sftp.port}")
	private String sftpPort;
	
	@Value("${file.upload.sftp.username}")
	private String sftpUsername;
	
	@Value("${file.upload.sftp.password}")
	private String sftpPassword;
	
	@Value("${file.upload.sftp.mode}")
	private String sftpMode;
	
	@Value("${file.upload.sftp.root}")
	private String rootDirectory;
	
}
