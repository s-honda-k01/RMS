DROP TABLE IF EXISTS TR_DAILYREPORT;
DROP TABLE IF EXISTS MT_REPORTSTATUS;
DROP TABLE IF EXISTS MT_USER;
DROP TABLE IF EXISTS MT_COMPANY;
DROP TABLE IF EXISTS MT_ROLE;

CREATE TABLE MT_ROLE(
		ROLECODE                      		VARCHAR(2)		 NOT NULL		 PRIMARY KEY,
		ROLENAME                      		VARCHAR(10)		 NOT NULL
);

insert into MT_ROLE values('1','ADMIN');
insert into MT_ROLE values('2','TRAINER');
insert into MT_ROLE values('3','STUDENT');
insert into MT_ROLE values('4','CUSTOMER');

CREATE TABLE MT_COMPANY(
		COMPANYCODE                   		VARCHAR(4)		 NOT NULL		 PRIMARY KEY,
		COMPANYNAME                   		VARCHAR(50)		 NOT NULL
);

insert into MT_COMPANY values('9999','PCIソリューションズ');
insert into MT_COMPANY values('9998','sample2');
insert into MT_COMPANY values('9997','sample1');

CREATE TABLE MT_USER(
		USERCODE                      		VARCHAR(8)		 NOT NULL		 PRIMARY KEY,
		LASTNAME                      		VARCHAR(10)		 NOT NULL,
		FIRSTNAME                     		VARCHAR(10)		 NOT NULL,
		ROLECODE                      		VARCHAR(2)		 NOT NULL,
		COMPANYCODE                   		VARCHAR(4)		 NOT NULL,
		PASSWORD                      		VARCHAR(256)		 NOT NULL,
  FOREIGN KEY (ROLECODE) REFERENCES MT_ROLE (ROLECODE),
  FOREIGN KEY (COMPANYCODE) REFERENCES MT_COMPANY (COMPANYCODE)
);

CREATE TABLE MT_REPORTSTATUS(
		REPORTSTATUSCODE              		VARCHAR(2)		 NOT NULL		 PRIMARY KEY,
		REPORTSTATUS                  		VARCHAR(10)		 NOT NULL
);

insert into mt_reportstatus values('1','未確認');
insert into mt_reportstatus values('2','確認済');
insert into mt_reportstatus values('3','要修正');

CREATE TABLE TR_DAILYREPORT(
		DAILYREPORTID                 		INT		 NOT NULL		 PRIMARY KEY AUTO_INCREMENT,
		REGDATE                       		DATE		 NOT NULL,
		PLAN                          		TEXT		 NULL ,
		RESULT                        		TEXT		 NOT NULL,
		REMARK                        		TEXT		 NOT NULL,
		USERCODE                      		VARCHAR(8)		 NULL ,
		REPORTSTATUSCODE              		VARCHAR(2)		 NULL ,
		TRAINERCODE                   		VARCHAR(8)		 NULL ,
  FOREIGN KEY (USERCODE) REFERENCES MT_USER (USERCODE),
  FOREIGN KEY (REPORTSTATUSCODE) REFERENCES MT_REPORTSTATUS (REPORTSTATUSCODE),
  FOREIGN KEY (TRAINERCODE) REFERENCES MT_USER (USERCODE)
);

