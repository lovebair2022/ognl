package org.ognl.test;

import junit.framework.TestCase;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.SimpleNode;

/**
 * Tests for {@link ognl.ASTSequence}.
 */
public class ASTSequenceTest extends TestCase {

    public void test_isSequence() throws Exception {
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null);

        SimpleNode node = (SimpleNode) Ognl.parseExpression("#name");
        assertFalse(node.isSequence(context));

        node = (SimpleNode) Ognl.parseExpression("#name = 'boo', System.out.println(#name)");
        assertTrue(node.isSequence(context));

        node = (SimpleNode) Ognl.parseExpression("#name['foo'] = 'bar'");
        assertFalse(node.isSequence(context));
    }

}
