package com.api.common.entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.api.common.utils.GaeUtil;
import com.api.common.utils.HashUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id","modifiedAt"})
public abstract class AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 7407155022997843530L;

	@Id
	protected String id;

	@Index
	protected long createdAt;

	@Index
	protected long modifiedAt;

	@OnSave
	public void updateTimeStamps() {

		this.modifiedAt = System.currentTimeMillis();
		if (this.createdAt <= 0)
			this.createdAt = modifiedAt;
	}

	@OnSave
	public void updateETagInCache(){
		GaeUtil.updateEntityETagInCache(this);
	}

	public String hash() {
		return HashUtil.md5Hash(String.valueOf(id) + modifiedAt);
	}
}
