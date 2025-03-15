package com.tom.process.config;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultFactory {
	private static SqlSessionFactory sqlSessionFactory;
	
	public ResultFactory() {
		String resource = "SQLMapConfig.xml";
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			if(sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
		}
	}
	
	public List<?> querylist(String sqlID,Object valueObject){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			List<?> resultList = sqlSession.selectList(sqlID, valueObject);
			return resultList; 
		}finally {
			sqlSession.close();
		}
	}
	
	public List<?> querylist(String sqlID){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			List<?> resultList = sqlSession.selectList(sqlID);
			return resultList; 
		}finally {
			sqlSession.close();
		}
	}
	
	public Object queryObject(String sqlID,Object valueObject){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			List<?> resultObject = sqlSession.selectOne(sqlID, valueObject);
			return resultObject; 
		}finally {
			sqlSession.close();
		}
	}
	
	public int insertObject(String sqlID,Object valueObject){
		int row = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			row = sqlSession.insert(sqlID, valueObject);
			sqlSession.commit();
			return row; 
		}finally {
			sqlSession.close();
		}
	}
	
	public int updateObject(String sqlID,Object valueObject){
		int row = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			row = sqlSession.update(sqlID, valueObject);
			sqlSession.commit();
			return row; 
		}finally {
			sqlSession.close();
		}
	}
	
	public int deleteObject(String sqlID,Object valueObject){
		int row = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			row = sqlSession.update(sqlID, valueObject);
			sqlSession.commit();
			return row; 
		}finally {
			sqlSession.close();
		}
	}
}
