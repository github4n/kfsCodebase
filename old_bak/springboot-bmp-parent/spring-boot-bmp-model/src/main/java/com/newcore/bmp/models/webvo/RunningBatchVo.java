package com.newcore.bmp.models.webvo;

public class RunningBatchVo extends BatchVO {
	String topic; //业务系统
    String proCode;	  //机构号
    String proName;		//机构名称
    String batchId; //作业ID
    String batchName; //作业名称
    String whereClause; //运行条件
    String startExecTime; //开始运行时间
    String endProcRecord; //完成数量
    String errProcRecord; //错误数量
    String execProcRecord; //已处理数量

    String oclkBranchNo; //运行人员机构
    String oclkClerkNo; //运行人员工号
    
    String batchType; //批作业类型
    String batchTypeDetail; //批作业类型

    String endExecTime; 
    String execStat;
    


    

    public String getBatchTypeDetail() {
		return batchTypeDetail;
	}

	public void setBatchTypeDetail(String batchTypeDetail) {
		this.batchTypeDetail = batchTypeDetail;
	}

	public String getEndExecTime() {
		return endExecTime;
	}

	public void setEndExecTime(String endExecTime) {
		this.endExecTime = endExecTime;
	}

	public String getExecStat() {
		return execStat;
	}

	public void setExecStat(String execStat) {
		this.execStat = execStat;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public String getExecProcRecord() {
		return execProcRecord;
	}

	public void setExecProcRecord(String execProcRecord) {
		this.execProcRecord = execProcRecord;
	}

	public String getTopic() {
    	return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
   
    public String getProCode() {
    	return proCode;
    }
    
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    
    
    public String getProName() {
    	return proName;
    }
    
    public void setProName(String proName) {
        this.proName = proName;
    }
    
    public String getBatchId() {
    	return batchId;
    }
    
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    public String getBatchName() {
    	return batchName;
    }
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    
    public String getWhereClause() {
    	return whereClause;
    }
    
    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
    
    public String getStartExecTime() {
    	return startExecTime;
    }
    
    public void setStartExecTime(String startExecTime) {
        this.startExecTime = startExecTime;
    }
    
    public String getEndProcRecord() {
    	return endProcRecord;
    }
    
    public void setEndProcRecord(String endProcRecord) {
        this.endProcRecord = endProcRecord;
    }
    
    public String getErrProcRecord() {
    	return errProcRecord;
    }
    
    public void setErrProcRecord(String errProcRecord) {
        this.errProcRecord = errProcRecord;
    }
    
    public String getOclkBranchNo() {
    	return oclkBranchNo;
    }
    
    public void setOclkBranchNo(String oclkBranchNo) {
        this.oclkBranchNo = oclkBranchNo;
    }
    
    public String getOclkClerkNo() {
    	return oclkClerkNo;
    }
    
    public void setOclkClerkNo(String oclkClerkNo) {
        this.oclkClerkNo = oclkClerkNo;
    }
}

