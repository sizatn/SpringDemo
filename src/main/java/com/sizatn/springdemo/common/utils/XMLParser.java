package com.sizatn.springdemo.common.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLParser {
	
	private static final Log log = LogFactory.getLog(XMLParser.class);
	
	private String xmlString = null;
	
	private File xmlFile = null;
	
	private String encoding = "UTF-8";
	
	private Document document = null;
	
	private Map propertyCache = new HashMap();
	
	public XMLParser(String xmlString) throws Exception{
		this.xmlString = xmlString;
		this.document = this.parseDocument(this.xmlString);
	}
	
	public XMLParser(String xmlString,String encoding) throws Exception{
		this.xmlString = xmlString;
		this.encoding = encoding;
		this.document = this.parseDocument(this.xmlString);
	}	
	
	public XMLParser(File xmlFile) throws Exception{
		this.xmlFile = xmlFile;
		this.document = this.parseDocument(this.xmlFile);
	}	
	
	/**
	 * Returns the value of the specified property.
	 * @param name the name of the property to get.
	 * @return the value of the specified property.
	 */
	public String getProperty(String name) {	
		if (propertyCache.containsKey(name)) {
			return (String) this.getPropertyCache().get(name);
		}		
		Element element = this.getDocument().getRootElement();
		return this.getProperty(element, name, name);
	}
	
	private String getProperty(Element element,String name,String key) {
		if(element==null) return null;	
		
		/*
		if (propertyCache.containsKey(key)) {
			return (String) this.getPropertyCache().get(key);
		}
		*/
		
		String resultValue = null;		
		String[] propName = this.parsePropertyName(name);
		int len = propName.length;
		for (int i = 0; i < len; i++) {
			//System.out.println(i+"\t"+propName[i]+"\t"+element);			
			if(i==len-1){
				resultValue = element.getTextTrim();			
			}
			else{
				String attributeValue = element.getAttributeValue(propName[i+1]);
				element = element.getChild(propName[i+1]);
				if(element==null && StringUtils.isNotEmpty(attributeValue) && i==len-2){
					resultValue = attributeValue;
					break;
				}
			}
			if(element==null){
				resultValue = null;
				break;
			}
		}
		
		if(resultValue!=null){
			this.getPropertyCache().put(key, resultValue);
		}
		return resultValue;
	}	
	
	public int getPropertyNum(String name){		
		return this.getPropertyNum(this.getDocument().getRootElement(), name);
	}
	
	public int getPropertyNum(String prefix,int no,String postfix){
		Element ele = this.getElement(prefix, no);
		return this.getPropertyNum(ele, postfix);
	}
	
	public String getProperty(String prefix,int no,String postfix) {		
		String key = prefix+"."+no+"."+postfix;
		if (this.getPropertyCache().containsKey(key)) {
			return (String) this.getPropertyCache().get(key);
		}		
		Element ele = this.getElement(prefix,no);
		if(ele!=null){
			return this.getProperty(ele,postfix,key);
		}
		return null;
	}
	
	public String getProperty(String prefix1,int no1,String prefix2,int no2,String postfix) {
		String key = prefix1+"."+no1+"."+prefix2+"."+no2+"."+postfix;
		if (this.getPropertyCache().containsKey(key)) {
			return (String) this.getPropertyCache().get(key);
		}
		Element ele = this.getElement(prefix1,no1);
		ele = this.getElement(ele,prefix2, no2);
		return this.getProperty(ele, postfix, key);
	}
	
	private Element getElement(Element element,String prefix,int no){
		String[] propName = parsePropertyName(prefix);
		int len = propName.length;
		List eleList = null;			
		for (int i = 0; i < len;) {
			//System.out.println(element);
			if(i==len-1){
				//if(element.getChild(propName[i])!=null) 
				eleList = element.getChildren();
				break;
			}
			else{			
				i++;
				element = element.getChild(propName[i]);			
				if (element == null) {
					break;		
				}
			}
		}
		
		if(eleList!=null){
			int eleSize = eleList.size();
			if(no>eleSize-1){
				return null;
			}
			Element tmpEle = (Element)eleList.get(no);
			return tmpEle;		
		}
				
		return null;		
	}
	
	private Element getElement(String prefix,int no) {
		return this.getElement(this.getDocument().getRootElement(), prefix, no);
		/*
		String[] propName = parsePropertyName(prefix);
		int len = propName.length;
		List eleList = null;
			
		Element element = this.getDocument().getRootElement();			
		for (int i = 0; i < len;) {
			//System.out.println(element);
			if(i==len-1){
				//if(element.getChild(propName[i])!=null) 
				eleList = element.getChildren();
				break;
			}
			else{			
				i++;
				element = element.getChild(propName[i]);			
				if (element == null) {
					break;		
				}
			}
		}
		
		if(eleList!=null){
			int eleSize = eleList.size();
			if(no>eleSize-1){
				return null;
			}
			Element tmpEle = (Element)eleList.get(no);
			return tmpEle;		
		}
				
		return null;
		*/
	}	
	
	private int getPropertyNum(Element element,String name){
		//System.out.println(element);
		int num = 0;
		String[] propName = this.parsePropertyName(name);
		int len = propName.length;
		for (int i = 0; i < len;) {
			if(i==len-1){
				num = element.getChildren().size();
				break;
			}
			else{	
				i++;
				//System.out.println("# = "+propName[i]);
				element = element.getChild(propName[i]);
				//System.out.println(i+"="+element);
				//System.out.println("==============");
				if (element == null) {						
					break;	
				}
			}
		}
		return num;		
	}	
	
	public void clear(){
		this.propertyCache.clear();
	}
	
	public Map getPropertyCache() {
		return propertyCache;
	}

	public void setPropertyCache(Map propertyCache) {
		this.propertyCache = propertyCache;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
		

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	private Document parseDocument(String xmlString) throws Exception{
		Document doc = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			DataUnformatFilter format = new DataUnformatFilter();
			builder.setXMLFilter(format);
			
			if(StringUtils.isEmpty(this.encoding)){
				doc = builder.build(new java.io.ByteArrayInputStream(xmlString.getBytes()));
			}
			else{
				doc = builder.build(new java.io.ByteArrayInputStream(xmlString.getBytes(this.encoding)));
			}
		}
		catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return doc;		
	}	
	
	private Document parseDocument(File xmlFile) throws Exception{
		Document doc = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			DataUnformatFilter format = new DataUnformatFilter();
			builder.setXMLFilter(format);
			doc = builder.build(xmlFile);
		}
		catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return doc;		
	}	
	
	/**
	 * Returns an array representation of the given Jive property. Jive
	 * properties are always in the format "prop.name.is.this" which would be
	 * represented as an array of four Strings.
	 * @param name the name of the Jive property.
	 * @return an array representation of the given Jive property.
	 */
	private String[] parsePropertyName(String name) {
		// Figure out the number of parts of the name (this becomes the size
		// of the resulting array).
		int size = 1;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '.') {
				size++;
			}
		}
		String[] propName = new String[size];
		// Use a StringTokenizer to tokenize the property name.
		StringTokenizer tokenizer = new StringTokenizer(name, ".");
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			propName[i] = tokenizer.nextToken();
			i++;
		}
		return propName;
	}
	
	public static void main(String[] args) {
//		String xml = "<request><head><target>road</target><type>create</type><encoding>GBK</encoding><title>新增路口信息</title></head><body><fields><id>234</id><name>解放路口</name><parentId>-1</parentId></fields></body></request>";
//		XMLParser parser = new XMLParser(xml);
//		System.out.println("value = "+parser.getProperty("request.head.type"));
		
		/*
		String xml1 = "<request><head version=\"1.0.0.0\" target=\"roleDept\" type=\"update\" encoding=\"UTF-8\"/><body><fields><roleId>roleIda</roleId><list><union id=\"2\" deptId=\"22\"/><union id=\"3\" deptId=\"33\"/></list></fields></body></request>";
		XMLParser parser = null;
		try {
			parser = new XMLParser(xml1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		//System.out.println("roleId = "+parser.getProperty("request.body.fields.roleId"));
		//System.out.println("num = "+parser.getPropertyNum("request.body.fields.list"));
		System.out.println("num = "+parser.getPropertyNum("request.body.fields.list"));
		System.out.println("value = "+parser.getProperty("request.body.fields.list", 1, "union.deptId"));
		*/
		
		/*
		String sqlConfigFile = "D:/work/tiip/standard/tiip_sanshui/project/tiipWeb/WebContent/WEB-INF/config/sql/oracle-9i.xml";
		log.debug("SQL配置文件："+sqlConfigFile);
		try {
			XMLParser parser = new XMLParser(new File(sqlConfigFile));
			System.out.println(parser.getProperty("param-config.punish.punishAnalysisReport"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		*/
		
		/*
		String sqlConfigFile = "D:/work/tiip/standard/tiip_v2/project/tiipWeb/WebContent/WEB-INF/config/version/oracle-9i.xml";
		try {
			XMLParser xmlParser = new XMLParser(new File(sqlConfigFile));
			int size = xmlParser.getPropertyNum("version-config");
			boolean found = true;
			System.out.println("size = "+size);
			for(int i=0;i<size;i++){
				if(found){
					String versionNo = xmlParser.getProperty("version-config", i, "version.no");
					int sqlSize = xmlParser.getPropertyNum("version-config", i, "version");
					for(int j=0;j<sqlSize;j++){					
						String sql = xmlParser.getProperty("version-config", i, "version", j, "sql");
						System.out.println(sql);
					}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		*//*
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?> <root><head><code>1</code> <message>数据下载成功!</message> </head> <body> <rownum>1</rownum> <vehicle id=\"0\"> <ltgg>215/75R15</ltgg> <hlj>1430</hlj> <xsjg>0</xsjg> <clpp2>#</clpp2> <ccrq>2003-05-01</ccrq> <fprq>1900-01-01</fprq> <fzrq>2003-08-11</fzrq> <zsxxdz>陕西西安莲湖区北院111号中院50号</zsxxdz>" +
		"<hgzbh>#</hgzbh>"+ 
		"<zj>3380</zj>"+ 
		"<syxz>A</syxz>"+ 
		"<hdzk>5</hdzk>"+ 
		"<glbm>610100</glbm>"+ 
		"<csys>G</csys>"+ 
		"<zzcmc>保定长城汽车股份有限公司</zzcmc>"+ 
		"<sfzmhm>610104196003071114</sfzmhm>"+ 
		"<lsh>#</lsh>"+ 
		"<rlzl>A</rlzl>"+ 
		"<qlj>1450</qlj>"+ 
		"<clly>1</clly>"+ 
		"<cwkc>5035</cwkc>"+ 
		"<xzqh>610100</xzqh>"+ 
		"<zdyzt>#</zdyzt>"+ 
		"<hbdbqk>#</hbdbqk>"+ 
		"<qpzk>2</qpzk>"+ 
		"<clsbdh>LZACA2GA13A001888</clsbdh>"+ 
		"<lts>4</lts>"+ 
		"<jbr>#</jbr>"+ 
		"<yzbm1>710012</yzbm1>"+ 
		"<cwkk>1740</cwkk>"+ 
		"<bz>#</bz>"+ 
		"<qmbh>#</qmbh>"+ 
		"<cwkg>1670</cwkg>"+ 
		"<xsrq>1900-01-01</xsrq>"+ 
		"<zdjzshs>0</zdjzshs>"+ 
		"<hxnbgd>405</hxnbgd>"+ 
		"<zzxxdz>#</zzxxdz>"+ 
		"<syr>张民强</syr>"+ 
		"<fzjg>陕A</fzjg>"+ 
		"<gcjk>A</gcjk>"+ 
		"<hpzl>02</hpzl>"+ 
		"<pl>2237</pl>"+ 
		"<hdfs>A</hdfs>"+ 
		"<yxqz>2009-07-31</yxqz>"+ 
		"<zs>2</zs>"+ 
		"<xgzl>#</xgzl>"+ 
		"<lxdh>8402838</lxdh>"+ 
		"<hphm>A12345</hphm>"+ 
		"<zzl>2245</zzl>"+ 
		"<clxh>CC1021AR</clxh>"+ 
		"<zzz>#</zzz>"+ 
		"<bzcs></bzcs>"+ 
		"<yxh></yxh>"+ 
		"<gbthps>10</gbthps>"+ 
		"<xsdw>#</xsdw>"+ 
		"<fdjxh>491QE</fdjxh>"+ 
		"<pzbh1>0126488</pzbh1>"+ 
		"<fhgzrq>2008-09-22</fhgzrq>"+ 
		"<gl>0</gl>"+ 
		"<bxzzrq>2009-09-24</bxzzrq>"+ 
		"<zzxzqh>#</zzxzqh>"+ 
		"<zqyzl>0</zqyzl>"+ 
		"<llpz2>#</llpz2>"+ 
		"<hxnbkd>1465</hxnbkd>"+ 
		"<jkpzhm>#</jkpzhm>"+ 
		"<fdjrq>2003-08-11</fdjrq>"+ 
		"<zzg>156</zzg>"+ 
		"<nszmbh>610025843</nszmbh>"+ 
		"<cllx>H31</cllx>"+ 
		"<jkpz>#</jkpz>"+ 
		"<zbzl>1420</zbzl>"+ 
		"<fdjh>D030486980</fdjh>"+ 
		"<djzsbh>610000212430</djzsbh>"+ 
		"<bdjcs>0</bdjcs>"+ 
		"<djrq>2008-09-19</djrq> "+ 
		"<gxrq>2008-09-22</gxrq> "+ 
		"<ccdjrq>2003-07-28</ccdjrq> "+ 
		"<hdzzl>500</hdzzl> "+ 
		"<hpzk>0</hpzk> "+ 
		"<hxnbcd>1865</hxnbcd> "+ 
		"<qzbfqz>2018-07-28</qzbfqz> "+ 
		"<dybj>0</dybj> "+ 
		"<zsxzqh>610104</zsxzqh> "+ 
		"<sfzmmc>A</sfzmmc> "+ 
		"<pzbh2>#</pzbh2> "+ 
		"<hmbh>#</hmbh> "+ 
		"<llpz1>A</llpz1> "+ 
		"<dabh>#</dabh> "+ 
		"<yzbm2>#</yzbm2> "+ 
		"<zxxs>1</zxxs> "+ 
		"<nszm>1</nszm> "+ 
		"<zt>A</zt> "+ 
		"<bpcs>0</bpcs> "+ 
		"<clpp1>长城</clpp1>"+
		"</vehicle> </body></root>";
		XMLParser parser;
		try {
			parser = new XMLParser(xml);
			System.out.println(Integer.parseInt(parser.getProperty("root.body.rownum")));
			System.out.println(parser.getProperty("root.body.vehicle.clpp1"));
			System.out.println(parser.getProperty("root.body.vehicle.clpp2"));
			System.out.println(parser.getProperty("root.body.vehicle.zsxxdz"));
			System.out.println(parser.getProperty("root.body.vehicle.clsbdh"));
			System.out.println(parser.getProperty("root.body.vehicle.csys"));
			System.out.println(parser.getProperty("root.body.vehicle.syr"));
			System.out.println(parser.getProperty("root.body.vehicle.lxdh"));
			System.out.println(parser.getProperty("root.body.vehicle.yzbm1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		String xml= new String("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+"<response><head><code>0</code><message>成功</message></head>"
				+"<body><xh>44010094394814</xh><hpzl>02</hpzl><hphm>A88888</hphm>" 
				+"<syr>广州金利来城市房产有限公司 (曾智雄)</syr><zsxxdz>广州金利来城市房产有限公司 </zsxxdz>"
				+"<lxdh>38780800-816</lxdh><cllx>k33</cllx><clpp1>劳斯莱斯</clpp1><csys>H</csys>" 
				+"<clxh>SCAZN00CZRCX</clxh><yzbm1>000000</yzbm1></body></response>");
		XMLParser parser;
		try {
			System.out.println(new String(xml.getBytes("UTF-8"),"GBK"));
			parser = new XMLParser(xml);
			System.out.println(parser.getProperty("response.head.code"));
			System.out.println(parser.getProperty("response.head.message"));
			System.out.println(parser.getProperty("response.body.syr"));
			System.out.println(parser.getProperty("response.body.zsxxdz"));
			System.out.println(parser.getProperty("response.body.lxdh"));
			System.out.println(parser.getProperty("response.body.clpp1"));
			System.out.println(parser.getProperty("response.body.clxh"));
			System.out.println(parser.getProperty("response.body.csys"));
			System.out.println(parser.getProperty("response.body.cllx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
