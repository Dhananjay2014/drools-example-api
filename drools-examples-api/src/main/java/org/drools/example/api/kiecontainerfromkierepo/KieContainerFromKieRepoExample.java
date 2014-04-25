package org.drools.example.api.kiecontainerfromkierepo;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.PrintStream;

public class KieContainerFromKieRepoExample {

    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();

        // Install example1 in the local maven repo before to do this
        //KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("org.drools.example.api.kiecontainerfromkierepo", "ABC", "0.0.1"));
        ReleaseId releaseId = ks.newReleaseId( "drools-example-api", "drools-example-api", "0.0.1" );
    	KieContainer kContainer = ks.newKieContainer( releaseId );

        KieSession kSession = kContainer.newKieSession("ksession1");
        kSession.setGlobal("out", out);

        Object msg1 = createMessage(kContainer, "Dave", "Hello, HAL. Do you read me, HAL?");
        kSession.insert(msg1);
        kSession.fireAllRules();
    }

    public static void main(String[] args) {
        new KieContainerFromKieRepoExample().go(System.out);
    }

    private static Object createMessage(KieContainer kContainer, String name, String text) {
        Object o = null;
        try {
            Class cl = kContainer.getClassLoader().loadClass("org.drools.example.api.namedkiesession.Message");
            o = cl.getConstructor(new Class[]{String.class, String.class}).newInstance(name, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

}
