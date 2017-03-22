package home.study.common.db.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import home.study.common.HsConstants;
import home.study.system.HsLoginUserDto;
import home.study.system.HsSystemException;
import home.study.system.NcuQueryException;

/**
 * All DAO need inherited from this class.
 */
public abstract class AbstractDAO<T> implements Serializable {
	
	private static final long serialVersionUID = 3681157774839724569L;

	//The class that the dao will process.
	private Class<T> entityClass;
	
	@Inject
	//@SeaDatabase
	protected EntityManager em;

	public AbstractDAO(Class<T> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	/**
	 * Find entity list by entity
	 * @param entity
	 * @param isView
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> simpleQuery(T entity, boolean isView) {
		try{
			StringBuilder sql = new StringBuilder();
			StringBuilder where = new StringBuilder();
			List<Object> paramList = new ArrayList<Object>();
			
			Table table = this.entityClass.getAnnotation(Table.class);
			if(table != null){
				List<String> pkList = new ArrayList<String>();
				List<String> columnList = new ArrayList<String>();
				List<String> fieldList = new ArrayList<String>();
				getColumnFieldNameListByEntityObj(entity, pkList, columnList, fieldList);
				columnList.addAll(pkList);
				for(int i = 0; i < pkList.size(); i++){
					String column = pkList.get(i);
					if(sql.length() > 0){
						sql.append(", ");
					}
					sql.append(column);
					
					if(where.length() > 0){
						where.append(" AND ");
					}
					Object value = getSimplePropertyByProperty(entity, fieldList.get(i));
					if(value instanceof Date || value instanceof java.sql.Date){
						column = "TRUNC(" + column + ")";
					}
					paramList.add(value);
					where.append(column).append(" = ?").append(paramList.size());
				}
				sql.append(" FROM ");
				if(isView){
					sql.append("V_");
				}
				sql.append(table.name());
			}
			
			Query query = em.createNativeQuery("SELECT " + sql.append(" WHERE ").append(where).toString(), this.entityClass);
			for(int i = 0; i < paramList.size(); i++){
				if(paramList.get(i) instanceof Date ||
						paramList.get(i) instanceof java.sql.Date){
					Calendar dateParam = Calendar.getInstance();
					dateParam.setTime((Date) paramList.get(i));
					query.setParameter(i + 1, dateParam, TemporalType.DATE);
				}
				else if(paramList.get(i) instanceof Timestamp){
					Calendar dateParam = Calendar.getInstance();
					dateParam.setTime((Date) paramList.get(i));
					query.setParameter(i + 1, dateParam, TemporalType.TIMESTAMP);
				}
				else{
					query.setParameter(i + 1, paramList.get(i));
				}
			}
			
			return query.getResultList();
		}
		catch(Exception e){
			throw new HsSystemException(e);
		}
	}

	/**
	 * Find record by primary key
	 * @param id
	 * @return
	 */
	public T find(Object id, boolean isView) {
		T rs = null;
		try{
			rs = em.find(entityClass, id);
			if(rs == null){
				return null;
			}
			if(isView){
				Method method = rs.getClass().getDeclaredMethod("getDelFlg");
				if(!"0".equals(method.invoke(rs))){
					return null;
				}
			}
		}
		catch(Exception e){
			throw new HsSystemException(e);
		}
		return rs;
	}

	/**
	 * Find all records in table
	 * @return
	 */
	public List<T> findAll() {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Find specific row count of record
	 * @param range
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findRange(int[] range) {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return  q.getResultList();
	}

	/**
	 * Find row count
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(entityClass);
		cq.select(em.getCriteriaBuilder().count(rt));
		javax.persistence.Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
	
	/**
	 * Insert by entity object 
	 * and if the table field contains: CRT_YMDHMS,UPD_YMDHMS,they default value are SYSTIMESTAMP 
	 * and if the table field contains: CRT_L_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: CRT_USER,UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: CRT_TM_ZONE,UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: CRT_PGM,UPD_PGM,they default value are loginUserDto.getProgramId()
	 * and if the table field contains: DEL_FLG,and it's value is blank,then set '0' into it
	 * @param entity
	 * @param loginUserDto
	 * @return insert result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int insertByEntity(T entity, HsLoginUserDto loginUserDto) throws NcuQueryException{
		return insertByEntity(entity, loginUserDto, null);
	}
	
	/**
	 * Insert by entity object 
	 * and if the table field contains: CRT_YMDHMS,UPD_YMDHMS,they default value are SYSTIMESTAMP 
	 * and if the table field contains: CRT_L_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: CRT_USER,UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: CRT_TM_ZONE,UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: CRT_PGM,UPD_PGM,they default value are loginUserDto.getProgramId()
	 * and if the table field contains: DEL_FLG,and it's value is blank,then set '0' into it
	 * @param entities
	 * @param loginUserDto
	 * @return insert result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int insertByEntity(List<T> entities, HsLoginUserDto loginUserDto) throws NcuQueryException{
		return insertByEntity(entities, loginUserDto, null);
	}
	
	/**
	 * Insert by entity object
	 * and if the table field contains: CRT_YMDHMS,UPD_YMDHMS,they default value are SYSTIMESTAMP 
	 * and if the table field contains: CRT_L_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: CRT_USER,UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: CRT_TM_ZONE,UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: CRT_PGM,UPD_PGM,they default value are loginUserDto.getProgramId()
	 * and if the table field contains: DEL_FLG,and it's value is blank,then set '0' into it 
	 * 
	 * but if you define some special field value in specialValueMap,the insert SQL will use the field value which you defined in specialValueMap
	 * 
	 * @param entity
	 * @param loginUserDto
	 * @param specialValueMap
	 * @return insert result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int insertByEntity(T entity, HsLoginUserDto loginUserDto, Map<String,Object> specialValueMap) throws NcuQueryException{
		List<T> entities = new ArrayList<T>();
		entities.add(entity);
		return insertByEntity(entities, loginUserDto, specialValueMap);
	}

	/**
	 * Insert by entity object 
	 * and if the table field contains: CRT_YMDHMS,UPD_YMDHMS,they default value are SYSTIMESTAMP 
	 * and if the table field contains: CRT_L_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: CRT_USER,UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: CRT_TM_ZONE,UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: CRT_PGM,UPD_PGM,they default value are loginUserDto.getProgramId()
	 * and if the table field contains: DEL_FLG,and it's value is blank,then set '0' into it 
	 * 
	 * but if you define some special field value in specialValueMap,the insert SQL will use the field value which you defined in specialValueMap
	 * 
	 * @param entities
	 * @param loginUserDto
	 * @param specialValueMap
	 * @return insert result
	 * @throws NcuQueryException
	 */
	public int insertByEntity(List<T> entities, HsLoginUserDto loginUserDto, Map<String,Object> specialValueMap) throws NcuQueryException{
		try {
			if(entities == null || entities.isEmpty()){
				return 0;
			}
			List<Object> paramList = new ArrayList<Object>();
			StringBuffer insertSQL = new StringBuffer("INSERT ALL");
			
			T oneEnt = entities.get(0);
			Table table = oneEnt.getClass().getAnnotation(Table.class);
			String tableName = "";
			if(table!=null){
				tableName = table.name();
			}
			List<String> columnNameList = new ArrayList<String>();
			List<String> fieldNameList = new ArrayList<String>();
			getColumnFieldNameListByEntityObj(oneEnt,null,columnNameList,fieldNameList);
			if("".equals(tableName)||columnNameList.isEmpty()){
				return 0;
			}
			if(specialValueMap==null){
				specialValueMap = new HashMap<String,Object>();
			}
			
			convertStringMapKeyToUpercase(specialValueMap);
			
			if(!specialValueMap.containsKey("CRT_YMDHMS")){
				specialValueMap.put("CRT_YMDHMS", "SYSTIMESTAMP");
			}
			if(!specialValueMap.containsKey("UPD_YMDHMS")){
				specialValueMap.put("UPD_YMDHMS", "SYSTIMESTAMP");
			}
			if(!specialValueMap.containsKey("CRT_L_YMDHMS")){
				specialValueMap.put("CRT_L_YMDHMS", "SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "'");
			}
			if(!specialValueMap.containsKey("UPD_L_YMDHMS")){
				specialValueMap.put("UPD_L_YMDHMS", "SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "'");
			}
			for(T entity : entities){
				if(columnNameList.indexOf("CRT_USER")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("CRT_USER")),loginUserDto.getUserCode());
				}
				if(columnNameList.indexOf("CRT_TM_ZONE")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("CRT_TM_ZONE")),loginUserDto.getUserTZCode());
				}
				if(columnNameList.indexOf("CRT_PGM")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("CRT_PGM")),loginUserDto.getProgramId());
				}
				if(columnNameList.indexOf("UPD_USER")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_USER")),loginUserDto.getUserCode());
				}
				if(columnNameList.indexOf("UPD_TM_ZONE")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_TM_ZONE")),loginUserDto.getUserTZCode());
				}
				if(columnNameList.indexOf("UPD_PGM")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_PGM")),loginUserDto.getProgramId());
				}
				if(columnNameList.indexOf("DEL_FLG")>0&&StringUtils.isBlank((String)getSimplePropertyByProperty(entity, fieldNameList.get(columnNameList.indexOf("DEL_FLG"))))){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("DEL_FLG")),"0");
				}
				
				insertSQL.append(" INTO ").append(tableName);
				insertSQL.append(" ( ").append(StringUtils.join(columnNameList.iterator(), ",")).append(" )");
				insertSQL.append(" VALUES (");
				int nameIndx=0;
				for(String columnName:columnNameList){
					if(nameIndx>0){
						insertSQL.append(",");
					}
					if(specialValueMap.containsKey(columnName)){
						insertSQL.append(specialValueMap.get(columnName));
					}else{
						Object value = getSimplePropertyByProperty(entity, fieldNameList.get(nameIndx));
						if(value != null){
							paramList.add(value);
							insertSQL.append("?"+paramList.size()+" ");
						}
						else{
							insertSQL.append("NULL ");
						}
					}
					nameIndx++;
				}
				insertSQL.append(" )");
			}
			insertSQL.append("SELECT 1 FROM DUAL");
			Query query = em.createNativeQuery(insertSQL.toString());
			for(int i = 0; i < paramList.size(); i++){
				query.setParameter(i + 1, paramList.get(i));
			}
			return query.executeUpdate();
		} catch (Exception e) {
			throw new NcuQueryException(e);
		}
	}

	/**
	 * NOTE:if you want to use this Method,you should the id property value into the entity object!!!!
	 * 
	 * update data by entity object and the update property value, id property value of entity object will add into paramList 
	 * and if the table field contains: UPD_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP 
	 * 
	 * @param entity
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(T entity, HsLoginUserDto loginUserDto) throws NcuQueryException{
		return updateByEntity(entity,loginUserDto,null,null);
	}
	
	
	/**
	 * NOTE:If you want to use this Method,you should set the id property value into the entity object!!!!
	 * 
	 * remove data: update data by entity object with primary keys and set del_flg = '1'  
	 * 
	 * @param entity
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech044
	 */
	public int deleteByEntity(T entity, HsLoginUserDto loginUserDto) throws NcuQueryException{
		List<String> updateColumnNameList = new ArrayList<String>();
		updateColumnNameList.add("DEL_FLG"); 
		
		setSimpleProperty(entity, "delFlg", HsConstants.FLAG_YES);
		
		return updateByEntity(entity, loginUserDto, updateColumnNameList, null);
	}
	
	/**
	 * NOTE:if you want to use this Method,you should the id property value into the entity object!!!!
	 * 
	 * update data by entity object and the update property value, id property value of entity object will add into paramList 
	 * and if the table field contains: UPD_YMDHMS,UPD_L_YMDHMS,they default value are SYSTIMESTAMP 
	 * 
	 * @param entity
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(List<T> entities, HsLoginUserDto loginUserDto) throws NcuQueryException{
		return updateByEntity(entities,loginUserDto,null,null);
	}

	/**
	 * NOTE:if you want to use this Method,you should set the id property value into the entity object!!!!
	 * 
	 * update data by entity object and updateColumnNameList
	 * and if the table field contains: UPD_YMDHMS,it's default value are SYSTIMESTAMP 
	 * and if the table field contains: UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: UPD_PGM,they default value are loginUserDto.getProgramId()
	 * 
	 * @param entity
	 * @param loginUserDto
	 * @param updateColumnNameList
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(T entity, HsLoginUserDto loginUserDto, List<String> updateColumnNameList) throws NcuQueryException{
		return updateByEntity(entity,loginUserDto,updateColumnNameList,null);
	}

	/**
	 * NOTE:if you want to use this Method,you should the id property value into the entity object!!!!
	 * 
	 * update data by entity object and updateColumnNameList,and the update property value, id property value of entity object will add into paramList 
	 * and if the table field contains: UPD_YMDHMS,it's default value are SYSTIMESTAMP 
	 * and if the table field contains: UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: UPD_PGM,they default value are loginUserDto.getProgramId()
	 * 
	 * @param entities
	 * @param loginUserDto
	 * @param updateColumnNameList
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(List<T> entities, HsLoginUserDto loginUserDto, List<String> updateColumnNameList) throws NcuQueryException{
		return updateByEntity(entities,loginUserDto,updateColumnNameList,null);
	}

	/**
	 * NOTE:if you want to use this Method,you should set the id property value into the entity object!!!!
	 * 
	 * update data by entity object and updateColumnNameList
	 * and if the table field contains: UPD_YMDHMS,it's default value are SYSTIMESTAMP 
	 * and if the table field contains: UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: UPD_PGM,they default value are loginUserDto.getProgramId()
	 * 
	 * but if you define some special field value in specialValueMap,the update SQL will use the field value which you defined in specialValueMap
	 * 
	 * @param entity
	 * @param loginUserDto
	 * @param updateColumnNameList
	 * @param specialValueMap
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(T entity, HsLoginUserDto loginUserDto, List<String> updateColumnNameList, Map<String,Object> specialValueMap) throws NcuQueryException{
		List<T> entities = new ArrayList<T>();
		entities.add(entity);
		return updateByEntity(entities, loginUserDto, updateColumnNameList, specialValueMap);
	}
	
	/**
	 * NOTE:if you want to use this Method,you should the id property value into the entity object!!!!
	 * 
	 * update data by entity object and updateColumnNameList,and the property value, id property value of entity object will add into paramList 
	 * and if the table field contains: UPD_YMDHMS,it's default value are SYSTIMESTAMP 
	 * and if the table field contains: UPD_L_YMDHMS,they default value are SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "' 
	 * and if the table field contains: UPD_USER,they default value are loginUserDto.getUserCode()
	 * and if the table field contains: UPD_TM_ZONE,they default value are loginUserDto.getUserTZCode()
	 * and if the table field contains: UPD_PGM,they default value are loginUserDto.getProgramId()
	 * 
	 * but if you define some special field value in specialValueMap,the update SQL will use the field value which you defined in specialValueMap
	 * 
	 * @param entities
	 * @param loginUserDto
	 * @param updateColumnNameList
	 * @param specialValueMap
	 * @return the update result
	 * @throws NcuQueryException
	 * @author : etech086
	 */
	public int updateByEntity(List<T> entities, HsLoginUserDto loginUserDto, List<String> updateColumnNameList, Map<String,Object> specialValueMap) throws NcuQueryException{
		try {
			if(entities == null || entities.isEmpty()){
				return 0;
			}
			int resCnt = 0;
			T oneEnt = entities.get(0);
			Table table = oneEnt.getClass().getAnnotation(Table.class);
			String tableName = "";
			if(table!=null){
				tableName = table.name();
			}
			List<String> pkColumnNameList = new ArrayList<String>();
			List<String> columnNameList = new ArrayList<String>();
			List<String> fieldNameList = new ArrayList<String>();
			getColumnFieldNameListByEntityObj(oneEnt,pkColumnNameList,columnNameList,fieldNameList);
			if("".equals(tableName)||pkColumnNameList.isEmpty()||columnNameList.isEmpty()){
				return 0;
			}
			if(updateColumnNameList == null){
				updateColumnNameList = columnNameList;
			}
			convertStringListToUpercase(updateColumnNameList);
			if(updateColumnNameList.indexOf("UPD_YMDHMS")<0){
				updateColumnNameList.add("UPD_YMDHMS");
			}
			if(updateColumnNameList.indexOf("UPD_L_YMDHMS")<0){
				updateColumnNameList.add("UPD_L_YMDHMS");
			}
			if(updateColumnNameList.indexOf("UPD_USER")<0){
				updateColumnNameList.add("UPD_USER");
			}
			if(updateColumnNameList.indexOf("UPD_TM_ZONE")<0){
				updateColumnNameList.add("UPD_TM_ZONE");
			}
			if(updateColumnNameList.indexOf("UPD_PGM")<0){
				updateColumnNameList.add("UPD_PGM");
			}

			if(specialValueMap==null){
				specialValueMap = new HashMap<String,Object>();
			}
			
			convertStringMapKeyToUpercase(specialValueMap);
			if(!specialValueMap.containsKey("UPD_YMDHMS")){
				specialValueMap.put("UPD_YMDHMS", "SYSTIMESTAMP");
			}
			if(!specialValueMap.containsKey("UPD_L_YMDHMS")){
				specialValueMap.put("UPD_L_YMDHMS", "SYSTIMESTAMP AT TIME ZONE '" + loginUserDto.getUserTimeZone() + "'");
			}
			for (T entity : entities) {
				if(columnNameList.indexOf("UPD_USER")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_USER")),loginUserDto.getUserCode());
				}
				if(columnNameList.indexOf("UPD_TM_ZONE")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_TM_ZONE")),loginUserDto.getUserTZCode());
				}
				if(columnNameList.indexOf("UPD_PGM")>0){
					setSimpleProperty(entity, fieldNameList.get(columnNameList.indexOf("UPD_PGM")),loginUserDto.getProgramId());
				}

				List<Object> paramList = new ArrayList<Object>();
				StringBuffer updateSQL = new StringBuffer("UPDATE ");
				updateSQL.append(tableName);
				updateSQL.append(" SET ");
				int paramIndx=1;
				int nameIndx=0;
				for(String columnName:updateColumnNameList){
					if(columnNameList.indexOf(columnName.toUpperCase())<0){
						continue;
					}
					if(specialValueMap.containsKey(columnName)){
						if(nameIndx>0){
							updateSQL.append(", ");
						}
						updateSQL.append(columnName.toUpperCase()+"="+specialValueMap.get(columnName));
						nameIndx++;
					}else{
						Object value = getSimplePropertyByProperty(entity, fieldNameList.get(columnNameList.indexOf(columnName.toUpperCase())));
						if((columnName.equals("CRT_YMDHMS") ||
								columnName.equals("CRT_L_YMDHMS") ||
								columnName.equals("CRT_TM_ZONE") ||
								columnName.equals("CRT_USER") ||
								columnName.equals("CRT_PGM") ||
								columnName.equals("UPD_YMDHMS") ||
								columnName.equals("UPD_L_YMDHMS") ||
								columnName.equals("UPD_TM_ZONE") ||
								columnName.equals("UPD_USER") ||
								columnName.equals("UPD_PGM") ||
								columnName.equals("DEL_FLG")) && value == null){
							continue;
						}
						if(nameIndx>0){
							updateSQL.append(", ");
						}
						updateSQL.append(columnName.toUpperCase()+"=?"+paramIndx+"");
						paramList.add(value);
						paramIndx++;
						nameIndx++;
					}
				}
				updateSQL.append(" WHERE ");
				nameIndx=0;
				for(String columnName:pkColumnNameList){
					Object value = getSimplePropertyByProperty(entity, fieldNameList.get(columnNameList.indexOf(columnName)));
					if(value != null){
						if(nameIndx>0){
							updateSQL.append(" AND ");
						}
						if(value instanceof Date || value instanceof java.sql.Date){
							columnName = "TRUNC(" + columnName + ")";
						}
						updateSQL.append(columnName+"=?"+paramIndx+"");
						paramList.add(value);
						paramIndx++;
						nameIndx++;
					}
				}
				Query query = em.createNativeQuery(updateSQL.toString());
				for(int i = 0; i < paramList.size(); i++){
					if(paramList.get(i) instanceof Date ||
							paramList.get(i) instanceof java.sql.Date){
						Calendar dateParam = Calendar.getInstance();
						dateParam.setTime((Date) paramList.get(i));
						query.setParameter(i + 1, dateParam, TemporalType.DATE);
					}
					else if(paramList.get(i) instanceof Timestamp){
						Calendar dateParam = Calendar.getInstance();
						dateParam.setTime((Date) paramList.get(i));
						query.setParameter(i + 1, dateParam, TemporalType.TIMESTAMP);
					}
					else{
						query.setParameter(i + 1, paramList.get(i));
					}
				}
				resCnt += query.executeUpdate();
			}
			return resCnt;
		} catch (Exception e) {
			throw new NcuQueryException(e);
		}
	}

	/**
	 * delete by entities
	 * @param entity
	 */
	public void physicallyDeleteByEntity(List<T> entities){
		if(entities == null || entities.size() == 0){
			return;
		}
		
		try {
			StringBuilder sql = new StringBuilder("DELETE FROM ");
			StringBuilder where = new StringBuilder();
			List<Object> paramList = new ArrayList<Object>();
			Table table = this.entityClass.getAnnotation(Table.class);
			if(table != null){
				sql.append(table.name());
			}
			
			Map<String, List<Object>> nameAndValueList = new LinkedHashMap<String, List<Object>>();
			for (T e : entities) {
				List<String> pkList = new ArrayList<String>();
				List<String> columnList = new ArrayList<String>();
				getColumnFieldNameListByEntityObj(e, pkList, columnList, null);
				columnList.addAll(pkList);
				for (String column : columnList) {
					if(nameAndValueList.containsKey(column)){
						if(nameAndValueList.get(column) == null){
							continue;
						}
					}
					else{
						nameAndValueList.put(column, new ArrayList<Object>());
					}
					
					List<String> fieldNameList = new ArrayList<String>();
					getColumnFieldNameListByEntityObj(e, pkList, columnList, fieldNameList);
					Object value = getSimplePropertyByProperty(e, fieldNameList.get(columnList.indexOf(column)));
					if(value != null){
						nameAndValueList.get(column).add(value);
					}
					else{
						// ignore null value
						nameAndValueList.put(column, null);
					}
				}
			}
			
			if(entities.size() == 1){
				// single entity
				for (String column : nameAndValueList.keySet()) {
					List<Object> valueList = nameAndValueList.get(column);
					if(valueList != null && valueList.size() > 0){
						if(where.length() > 0){
							where.append(" AND ");	
						}
						Object value = valueList.get(0);
						if(value instanceof Date || value instanceof java.sql.Date){
							column = "TRUNC(" + column + ")";
						}
						paramList.add(value);
						where.append(column).append(" = ?").append(paramList.size());
					}
				}
			}
			else{
				// multiple entities then use IN
				StringBuilder columns = new StringBuilder();
				StringBuilder values = new StringBuilder();
				columns.append('(');
				for(int i = 0; i < entities.size(); i++){
					values.append('(');
					for (String column : nameAndValueList.keySet()) {
						List<Object> valueList = nameAndValueList.get(column);
						if(valueList != null && valueList.size() > 0){
							columns.append(column).append(", ");
							paramList.add(valueList.get(i));
							values.append("?").append(paramList.size()).append(", ");
						}
					}
					values = values.replace(values.length() - 2, values.length(), "), ");
				}
				columns = columns.replace(columns.length() - 2, columns.length(), ")");
				
				values = values.replace(values.length() - 2, values.length(), "");
				where.append(columns).append("IN(").append(values).append(')');
			}
			
			Query query = em.createNativeQuery(sql.append(" WHERE ").append(where).toString());
			for(int i = 0; i < paramList.size(); i++){
				if(paramList.get(i) instanceof Date ||
						paramList.get(i) instanceof java.sql.Date){
					Calendar dateParam = Calendar.getInstance();
					dateParam.setTime((Date) paramList.get(i));
					query.setParameter(i + 1, dateParam, TemporalType.DATE);
				}
				else if(paramList.get(i) instanceof Timestamp){
					Calendar dateParam = Calendar.getInstance();
					dateParam.setTime((Date) paramList.get(i));
					query.setParameter(i + 1, dateParam, TemporalType.TIMESTAMP);
				}
				else{
					query.setParameter(i + 1, paramList.get(i));
				}
			}
			query.executeUpdate();
		} catch (Exception e) {
			throw new NcuQueryException(e);
		}
	}
	
	/**
	 * 
	 * read the PK column name into pkColumnNameList
	 * read all the column name into columnNameList
	 * read all the field name into fieldNameList
	 * 
	 * @param entity
	 * @param pkColumnNameList
	 * @param columnNameList
	 * @param fieldNameList
	 * @throws NcuQueryException
	 */
	private void getColumnFieldNameListByEntityObj(T entity, List<String> pkColumnNameList, List<String> columnNameList, List<String> fieldNameList) throws NcuQueryException{
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			if(pkColumnNameList==null){
				pkColumnNameList = new ArrayList<String>();
			}
			pkColumnNameList.clear();
			if(columnNameList==null){
				columnNameList = new ArrayList<String>();
			}
			columnNameList.clear();
			if(fieldNameList==null){
				fieldNameList = new ArrayList<String>();
			}
			fieldNameList.clear();
			for (Field field:fields) {
				EmbeddedId id = field.getAnnotation(EmbeddedId.class);
				if(id!=null){
					Object idObj = getSimplePropertyByProperty(entity, field.getName());
					if(idObj!=null){
						Field[] idFields = idObj.getClass().getDeclaredFields();
						for (Field idField:idFields) {
							Column column = idField.getAnnotation(Column.class); 
							if(column!=null){
								columnNameList.add(column.name().toUpperCase());
								pkColumnNameList.add(column.name().toUpperCase());
								fieldNameList.add(field.getName() + "." + idField.getName());
							}
						}
					}
				}else{
					Column column = field.getAnnotation(Column.class); 
					if(column!=null){
						columnNameList.add(column.name());
						fieldNameList.add(field.getName());
						if(field.getAnnotation(Id.class)!=null){
							pkColumnNameList.add(column.name().toUpperCase());
						}
					}
				}			
			}
		} catch (Exception e) {
			throw new NcuQueryException(e);
		}
	}	
	
	/**
	 * 
	 * read the PK column name into pkColumnNameList
	 * read all the column name into columnNameList
	 * read all the field name into fieldNameList
	 * 
	 * @param entity
	 * @param pkColumnNameList
	 * @param columnNameList
	 * @param fieldNameList
	 * @throws NcuQueryException
	 */
	protected List<String> getColumnNameListByEntityObj(T entity) throws NcuQueryException{
		List<String> columnNameList = new ArrayList<String>();
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (Field field:fields) {
				EmbeddedId id = field.getAnnotation(EmbeddedId.class);
				if(id!=null){
					Object idObj = getSimplePropertyByProperty(entity, field.getName());
					if(idObj!=null){
						Field[] idFields = idObj.getClass().getDeclaredFields();
						for (Field idField:idFields) {
							Column column = idField.getAnnotation(Column.class); 
							if(column!=null){
								columnNameList.add(column.name().toUpperCase());
							}
						}
					}
				}else{
					Column column = field.getAnnotation(Column.class); 
					if(column!=null){
						columnNameList.add(column.name());
					}
				}			
			}
		} catch (Exception e) {
			throw new NcuQueryException(e);
		}
		return columnNameList;
	}	
	
	/**
	 * convert the string element of strList to UpperCase
	 * @param strList
	 */
	private static void convertStringListToUpercase(List<String> strList){
		if(strList==null){
			return;
		}
		int size = strList.size();
		for(int i=0;i<size;i++){
			String str = strList.get(i);
			if (StringUtils.isNotBlank(str)) {
				strList.set(i, str.toUpperCase());
			}
		}	
	}

	/**
	 * convert the string key of strKeyMap to UpperCase
	 * @param strKeyMap
	 * @author : etech086
	 */	private void convertStringMapKeyToUpercase(Map<String,Object> strKeyMap){
		if(strKeyMap==null){
			return;
		}
		List<String> keyList = new ArrayList<String>();
		for(String key:strKeyMap.keySet()){
			keyList.add(key);
		}
		for(String key:keyList){
			Object obj = strKeyMap.get(key);
			strKeyMap.remove(key);
			strKeyMap.put(StringUtils.upperCase(key), obj);
		}		
	}
	private Object getSimplePropertyByProperty(Object obj, String property)throws HsSystemException{
		if(obj==null||property==null){
			return null;
		}
		int index = StringUtils.indexOf(property, ".");
		if(index<0){
			try {
				Method method = obj.getClass().getDeclaredMethod("get" + property.substring(0, 1).toUpperCase() + 
						property.substring(1));
				return method.invoke(obj);
			} catch (Exception e) {
				throw new HsSystemException(e);
			}
		}else{
			String cur_property = StringUtils.substring(property, 0,index);
			String next_property = StringUtils.substring(property, index+1);
			try {
				Method method = obj.getClass().getDeclaredMethod("get" + cur_property.substring(0, 1).toUpperCase() + 
						cur_property.substring(1));
				return getSimplePropertyByProperty(method.invoke(obj), next_property);
			} catch (Exception e) {
				throw new HsSystemException(e);
			}
		}
	}
	
	private void setSimpleProperty(Object obj, String property, Object objVal)throws HsSystemException{
		if(obj==null||property==null){
			return;
		}
		int index = StringUtils.indexOf(property, ".");
		if(index<0){
			try {
				Method method = obj.getClass().getDeclaredMethod("set" + property.substring(0, 1).toUpperCase() + 
						property.substring(1), objVal.getClass());
				method.invoke(obj, objVal);
			} catch (Exception e) {
				throw new HsSystemException(e);
			}
			return;
		}else{
			String cur_property = StringUtils.substring(property, 0,index);
			String next_property = StringUtils.substring(property, index+1);
			try {
				Object curObj = PropertyUtils.getProperty(obj, cur_property);
				if(curObj==null){
					Class<?> myClass = obj.getClass().getDeclaredField(cur_property).getType();
					curObj = myClass.newInstance();
					Method method = obj.getClass().getDeclaredMethod("set" + cur_property.substring(0, 1).toUpperCase() + 
							cur_property.substring(1), objVal.getClass());
					method.invoke(obj, curObj);
				}
				setSimpleProperty(curObj, next_property,objVal);
			} catch (Exception e) {
				throw new HsSystemException(e);
			}
		}
	}
}
