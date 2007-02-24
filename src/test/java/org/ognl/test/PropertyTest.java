//--------------------------------------------------------------------------
//  Copyright (c) 2004, Drew Davidson and Luke Blanshard
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are
//  met:
//
//  Redistributions of source code must retain the above copyright notice,
//  this list of conditions and the following disclaimer.
//  Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//  Neither the name of the Drew Davidson nor the names of its contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
//  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
//  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
//  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
//  OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
//  AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
//  THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
//  DAMAGE.
//--------------------------------------------------------------------------
package org.ognl.test;

import junit.framework.TestSuite;
import org.ognl.test.objects.Bean2;
import org.ognl.test.objects.Root;

import java.util.Arrays;

public class PropertyTest extends OgnlTestCase
{
    private static Root             ROOT = new Root();

    /* { ROOT, "map.(array[2] + size()).doubleValue()", new Double(ROOT.getArray()[2] + ROOT.getMap().size()) }, */

    private static Object[][]       TESTS = {
            { ROOT, "false", Boolean.FALSE},
            { ROOT, "map", ROOT.getMap() },
            { ROOT, "map.test", ROOT },
            { ROOT, "map[\"test\"]", ROOT },
            { ROOT, "map[\"te\" + \"st\"]", ROOT },
            { ROOT, "map[(\"s\" + \"i\") + \"ze\"]", ROOT.getMap().get(Root.SIZE_STRING) },
            { ROOT, "map[\"size\"]", ROOT.getMap().get(Root.SIZE_STRING) },
            { ROOT, "map[@org.ognl.test.objects.Root@SIZE_STRING]", ROOT.getMap().get(Root.SIZE_STRING) },
            { ROOT.getMap(), "list", ROOT.getList() },
            { ROOT, "map.array[0]", new Integer(ROOT.getArray()[0]) },
            { ROOT, "map.list[1]", ROOT.getList().get(1) },
            { ROOT, "map[^]", new Integer(99) },
            { ROOT, "map[$]", null },
            { ROOT.getMap(), "array[$]", new Integer(ROOT.getArray()[ROOT.getArray().length-1]) },
            { ROOT, "[\"map\"]", ROOT.getMap() },
            { ROOT.getArray(), "length", new Integer(ROOT.getArray().length) },
            { ROOT, "getMap().list[|]", ROOT.getList().get(ROOT.getList().size()/2) },
            { ROOT, "map.(array[2] + size())", new Integer(ROOT.getArray()[2] + ROOT.getMap().size()) },
            { ROOT, "map.(#this)", ROOT.getMap() },
            { ROOT, "map.(#this != null ? #this['size'] : null)", ROOT.getMap().get(Root.SIZE_STRING) },
            { ROOT, "map[^].(#this == null ? 'empty' : #this)", new Integer(99) },
            { ROOT, "map[$].(#this == null ? 'empty' : #this)", "empty" },
            { ROOT, "map[$].(#root == null ? 'empty' : #root)", ROOT },
            { ROOT, "((selected != null) && (currLocale.toString() == selected.toString())) ? 'first' : 'second'", "first" },
            { ROOT, "{stringValue, getMap()}", Arrays.asList(new Object[]{ROOT.getStringValue(), ROOT.getMap()})},
            { ROOT, "{'stringValue', map[\"test\"].map[\"size\"]}", Arrays.asList(new Object[]{"stringValue", ROOT.getMap().get("size")}) },
            { ROOT, "property.bean3.value + '(this.checked)'", "100(this.checked)"},
            { ROOT, "getIndexedProperty(property.bean3.map[\"bar\"])", ROOT.getArray()},
            { ROOT, "getProperty().getBean3()", ((Bean2)ROOT.getProperty()).getBean3()},
            { ROOT, "intValue", new Integer(0), new Integer(2), new Integer(2) },
            { ROOT, "! disabled", new Boolean(true)},
            { ROOT, "disabled", new Boolean(false), new Boolean(true), new Boolean(true)},
            { ROOT, "! disabled", new Boolean(false)},
            { ROOT, "property.bean3.value != null", Boolean.TRUE},
            { ROOT, "\"background-color:blue; width:\" + (currentLocaleVerbosity / 2) + \"px\"", "background-color:blue; width:43px"},
            { ROOT, "property.bean3.value >= 24", Boolean.TRUE},
            { ROOT, "renderNavigation ? '' : 'noborder'", "noborder" },
            { ROOT, "format('key', array)", "formatted" },
            { ROOT, "format('key', intValue)", "formatted" },
            { ROOT, "'disableButton(this,\"' + map.get('button-testing') + '\");clearElement(&quot;testFtpMessage&quot;)'",
                    "disableButton(this,'null');clearElement('testFtpMessage')" },
            { ROOT.getMap(), "!disableWarning", Boolean.TRUE},
            { ROOT.getMap(), "get('value').bean3.value", new Integer(((Bean2)ROOT.getMap().get("value")).getBean3().getValue())}
            
    };

    /*===================================================================
         Public static methods
       ===================================================================*/
    public static TestSuite suite()
    {
        TestSuite       result = new TestSuite();

        for (int i = 0; i < TESTS.length; i++) {

            if (TESTS[i].length == 5) {

                result.addTest(new PropertyTest((String)TESTS[i][1], TESTS[i][0], (String)TESTS[i][1], TESTS[i][2], TESTS[i][3], TESTS[i][4]));
            } else
                result.addTest(new PropertyTest((String)TESTS[i][1], TESTS[i][0], (String)TESTS[i][1], TESTS[i][2]));
        }
        return result;
    }

    /*===================================================================
         Constructors
       ===================================================================*/
    public PropertyTest()
    {
        super();
    }

    public PropertyTest(String name)
    {
        super(name);
    }

    public PropertyTest(String name, Object root, String expressionString, Object expectedResult, Object setValue, Object expectedAfterSetResult)
    {
        super(name, root, expressionString, expectedResult, setValue, expectedAfterSetResult);
    }

    public PropertyTest(String name, Object root, String expressionString, Object expectedResult, Object setValue)
    {
        super(name, root, expressionString, expectedResult, setValue);
    }

    public PropertyTest(String name, Object root, String expressionString, Object expectedResult)
    {
        super(name, root, expressionString, expectedResult);
    }
}