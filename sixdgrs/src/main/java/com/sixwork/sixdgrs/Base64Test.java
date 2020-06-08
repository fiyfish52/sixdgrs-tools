package com.sixwork.sixdgrs;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {

	
	public static void main(String[] argz) {
		
		String sql = "";
		sql = new String(Base64.decodeBase64("c2VsZWN0IHJlY2lkLGJpemZlZWRldGlkLGxvZ2lkLGN1c3RpZCxpbnZubyxzZXJpYWxubyxuYW1lLG9wY29kZSxwYXlmZWVzLHBheXdheSxmZWVjb2RlLHJmZWVjb2RlLGlmZWVjb2RlLG9wdGltZSBhcyBwYXl0aW1lLGZlZWtpbmQsc2NvcGV0eXBlLG1hcmtubyxiaXp0aW1lIGFzIGxvZ29wdGltZSxiaXpvcGVyaWQgYXMgb3BvcGVyLG9wZXJpZCBhcyBwYXlvcGVyLGJpemRlcHRpZCBhcyBvcGRlcHQsZGVwdGlkIGFzIHBheWRlcHQscGlkLHNhbGVzcGtnaWQsc2VydmlkLGRldm5hbWUsYml6YXJlYWlkIGFzIGFyZWFpZCxhcmVhaWQgYXMgdXNlcmFyZWFpZCxwYXRjaGlkLHBlcm1hcmssbG9naWNkZXZubyBhcyBjbWFjY3RubyxncmlkaWQsd2hsYWRkciBhcyBhZGRyZXNzIGZyb20gdmlld19iaXpfZGF0YV8yMDIwIHdoZXJlIDE9MSAgQU5EIHJlY2lkID49MjI4MTAyMDIxIEFORCByZWNpZCA8PTIzNDI0NDMwNCBBTkQgcmVjaWQgPjAgQU5EIG9wdGltZSA+PScyMDIwLTA1LTAxVDAwOjAwOjAwJyBBTkQgb3B0aW1lIDw9JzIwMjAtMDUtMTFUMDk6MDA6MDAnIG9yZGVyIGJ5IHJlY2lk".getBytes()));
		
		System.out.println(sql);

		String sql2 =new String(Base64.encodeBase64("select recid,bizfeedetid,logid,custid,invno,serialno,name,opcode,payfees,payway,feecode,rfeecode,ifeecode,optime as paytime,feekind,scopetype,markno,biztime as logoptime,bizoperid as opoper,operid as payoper,bizdeptid as opdept,deptid as paydept,pid,salespkgid,servid,devname,bizareaid as areaid,areaid as userareaid,patchid,permark,logicdevno as cmacctno,gridid,whladdr as address from view_biz_data_2020 where 1=1 AND optime >='2020-05-11T00:00:00' AND optime <='2020-05-11T09:00:00' #96#PLACE#96#".getBytes()));
	
		System.out.println(sql2);
	}
}
