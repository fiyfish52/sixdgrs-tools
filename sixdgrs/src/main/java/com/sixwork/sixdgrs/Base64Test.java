package com.sixwork.sixdgrs;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {

	
	public static void main(String[] argz) {
		
		String sql = "";
		sql = new String(Base64.decodeBase64("c2VsZWN0IHJlY2lkICxjaXR5X25hbWUgLGFyZWFfbmFtZSAsYmlnX2RlcHRuYW1lICxtaWRkbGVfZGVwdG5hbWUgLHdoZ3JpZG5hbWUgLGN1c3RpZCAsc3Vibm8gLHNlcnZpZCAsdG1fY3VzdF9uYW1lICxzZXhuYW1lICx0ZWwgLHdobGFkZHIgLG5ldG5vZGUgLG5vZGVuYW1lICxpZGVudGl0eV9uYW1lICxmZWVraW5kX25hbWUgLHNlcnZzdGF0dXNfbmFtZSAscGVybWFya19uYW1lICxzZXJ2dHlwZV9uYW1lICxzY29wZXR5cGVfbmFtZSAsc2lnbl9uYW1lICxuZXRzdHJ1Y3RfbmFtZSAsb3BlbnRpbWUgLG9wZXJfbmFtZSAsdXNlcHJvcF9uYW1lICxzdGJubyAsbG9naWNkZXZubyAsc3Ric3Via2luZF9uYW1lICxwbmFtZSAsZXRpbWUgLHN0b3Bsb25ndGltZSAsc2FsZXNjb2RlICxzYWxlc25hbWUgLHNhbGVzcGtnY29kZSAsc2FsZXNwa2duYW1lICxpc19yZXNfZGV2X21hdGNoICxwc3Vibm8gLHBjdXN0bmFtZSAsYmFuZHdpZHRoICxkZXZlbG9wX25hbWUgZnJvbSAgdHdfc2VydnN0X25tXzIwMjAwODI0IHdoZXJlIDE9MSBhbmQgaXNfZmxhZyA9MSAgICBBTkQgY2l0eSBpbiAoJ0hIJykgQU5EIGFyZWFpZCBpbiAoNTE2KSAgQU5EIHBlcm1hcmsgIGluICgnMScpICBBTkQgc2VydnR5cGUgaW4gKCcwJykgIEFORCBzZXJ2c3RhdHVzICBpbiAoJzInKSAgQU5EIHNpZ24gaW4gKCcwJykgIEFORCBpZGVudGl0eSBpbiAoJzAwJykgIzk2I1BMQUNFIzk2Iw==".getBytes()));
		
		System.out.println(sql);

//		String sql2 =new String(Base64.encodeBase64("select recid,bizfeedetid,logid,custid,invno,serialno,name,opcode,payfees,payway,feecode,rfeecode,ifeecode,optime as paytime,feekind,scopetype,markno,biztime as logoptime,bizoperid as opoper,operid as payoper,bizdeptid as opdept,deptid as paydept,pid,salespkgid,servid,devname,bizareaid as areaid,areaid as userareaid,patchid,permark,logicdevno as cmacctno,gridid,whladdr as address from view_biz_data_2020 where 1=1 AND optime >='2020-05-11T00:00:00' AND optime <='2020-05-11T09:00:00' #96#PLACE#96#".getBytes()));
//
//		System.out.println(sql2);
	}
}
