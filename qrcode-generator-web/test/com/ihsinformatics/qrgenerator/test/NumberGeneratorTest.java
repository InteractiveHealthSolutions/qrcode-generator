/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */

package com.ihsinformatics.qrgenerator.test;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.ihsinformatics.qrgenerator.NumberGenerator;
import com.ihsinformatics.util.CommandType;
import com.ihsinformatics.util.DatabaseUtil;

/**
 * @author haris.asif@ihsinformatics.com
 *
 */
public class NumberGeneratorTest {

    static NumberGenerator obj;
    static String url = "jdbc:mysql://localhost:3306/test";
    static String dbName = "test";
    static String driverName = "com.mysql.jdbc.Driver";
    static String userName = "root";
    static String password = "jingle94";

    @BeforeClass
    public static void onceExecutedBeforeAll()
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	DatabaseUtil dbUtil = new DatabaseUtil(url, dbName, driverName, userName, password);
	dbUtil.runCommandWithException(CommandType.CREATE,
		"CREATE TABLE IF NOT EXISTS _identifier (id varchar(255) NOT NULL, timestamp datetime NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=latin1");
	dbUtil.truncateTable("_identifier");
	obj = new NumberGenerator(url, dbName, driverName, userName, password);
    }

    @Test
    public void testGenerateSerial() {
	List<String> list = obj.generateSerial(2, 1, 10, false, true);
	assertTrue("Unable to generate serial codes of desired range.", list.size() == 10);
	Set<String> uniqueSet = new HashSet<String>();
	for (String s : list) {
	    uniqueSet.add(s);
	}
	assertTrue("Unable to generate unique serial codes of desired range.", list.size() == uniqueSet.size());
    }

    @Test
    public void testGenerateSerialWithPrefix() {
	NumberGenerator obj = new NumberGenerator(url, dbName, driverName, userName, password);
	List<String> list = obj.generateSerial("H01", 3, 1, 10, false, true);
	assertTrue("Unable to generate serial codes of desired range.", list.size() == 10);
	assertTrue("Unable to prepend prefix to serial codes.", list.get(0).startsWith("H01"));
	Set<String> uniqueSet = new HashSet<String>();
	for (String s : list) {
	    uniqueSet.add(s);
	}
	assertTrue("Unable to generate unique serial codes of desired range.", list.size() == uniqueSet.size());
    }

    @Test
    public void testGenerateSerialWithDate() {
	NumberGenerator obj = new NumberGenerator(url, dbName, driverName, userName, password);
	List<String> list = obj.generateSerial(null, 3, 1, 10, new Date(), "yyyyMM", false, true);
	assertTrue("Unable to generate serial codes of desired range.", list.size() == 10);
	Set<String> uniqueSet = new HashSet<String>();
	for (String s : list) {
	    uniqueSet.add(s);
	}
	assertTrue("Unable to generate unique serial codes of desired range.", list.size() == uniqueSet.size());
    }

    @Test
    @Ignore
    public void testGenerateRandom() throws Exception {
	List<String> list;
	int range = 10;
	list = obj.generateRandom(2, range, true, false, true);
	assertTrue("Unable to generate random codes of desired range.", list.size() == range);
    }

    @Test
    @Ignore
    public void testGenerateUniqueRandom() {
	List<String> list;
	list = obj.generateRandom(2, 10, true, false, true);
	assertTrue("Unable to generate random codes of desired range.", list.size() == 10);
	Set<String> uniqueSet = new HashSet<String>();
	for (String s : list) {
	    uniqueSet.add(s);
	}
	assertTrue("Unable to generate unique random codes of desired range.", list.size() == uniqueSet.size());
    }
}
