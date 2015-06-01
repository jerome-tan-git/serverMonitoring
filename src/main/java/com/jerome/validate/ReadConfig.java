package com.jerome.validate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.jerome.config.Config;

public class ReadConfig {
	public static Config readConfig(String _fileName) throws IOException
	{
		String JSONString = "";
		BufferedReader br = new BufferedReader(new FileReader(_fileName));
		String line =null;
		while((line = br.readLine())!=null)
		{
			JSONString += line;
		}
		return JSON.parseObject(JSONString, Config.class);
	}
}
