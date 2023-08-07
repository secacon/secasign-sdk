# Secasign-Box SDK

## Beschreibung

Dieses Projekt beinhaltet das Java SDK der Secasign-Box. Aufbau:

* Im Verzeichnis `src/main/java` befindet sich die eigentliche Implementierung des SDKs.
* Im Verzeichnis `src/test/java` sind diverse Unittests vorzufinden, wobei ein Unittest einen Use-Case darstellt (z.B. Anmeldung und grafische Signierung eines PDF-Dokumentes).
* Im Verzeichnis `data` liegen Ressourcen (PDF-Dokumente, grafische Signaturen etc.) welche durch den Integrator ausgetauscht werden können. Dort werden auch die signierten Dokumente abgespeichert.

## Anleitung

Für die Inbetriebnahme des Java SDKs müssen folgende Schritte ausgeführt werden:

1.) Die Secacon stellt dem Kunden die PDF-Dokumentation samt diesem Java SDK.

2.) In dieser PDF-Dokumentation sind die Zugangsdaten für die von der Secacon gestellte öffentlich erreichbare Testinstanz vorzufinden. Diese sind zu notieren und in die Datei `src/main/test/com/secacon/secasignbox/sdk/Values.java` einzutragen (Werte abändern). Wird dieser Schritt ausgelassen, so scheitern die Unittests mit einem Hinweis auf diese README.

3.) Nach dem Ändern der Werte können die einzelnen Unittests ausgeführt werden.

## Hinweise

* Das Java SDK wird als eigenständig lauffähiges Beispiel auf Basis von Java 17 und unter Verwendung von Jackson Object Mapper geliefert. Dem Kunden ist es überlassen, das Java SDK an seine eigenen Bedürfnisse anzupassen (z.B. Support für Java 8, anderer JSON Serializer etc.).

* In den Unittests wird immer auf die PDF-Dokumentation verwiesen (z.B. welche Werte sind setzbar/führen zu welchem Resultat). Es ist deshalb von zentraler Bedeutung, dass ein Integrator auch diese PDF-Dokumentation studiert (namentlich bei der Signierung von Dokumenten).

## Kontakt

Bei Fragen oder Unklarheiten kann Simon Wächter (simon.waechter@secacon.com) kontaktiert werden.
