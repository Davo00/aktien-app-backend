# aktien-app-backend
This is the backend of the pay and speculate with stock app

The technology we use:
- We use java Springboot
- We use an in memory database for testing purposes
- We can upgrade to a MySQL Database at a moments notice

The plan for our Project is as follows:

[Klassendiagram.pdf](https://github.com/Davo00/aktien-app-backend/files/6823431/Klassendiagram.pdf)

The Idea behind the App is as follows:

Die Web-App Idee ist, dass man online Gruppen erstellen kann. Gibt eine Person Geld für die Gruppe oder Teile der Gruppe aus (eine Runde Bier für alle, einen Einkauf für 3 der 5 Mitglieder) kann diese Transaktion und alle beteiligten in die App eingetragen werden. Diese müssen von anderen Mitgliedern bestätigt werden, z.b. 2/3 Mehrheit, damit nicht eine Person Ausgaben erfinden kann. 

Zu jeder Zeit kann die Gruppe abrechnen, woraufhin die App ausrechnet, welches Gruppenmitglied wem wie viel Geld schuldet (so optimiert, dass möglichst viele Ausgaben sich ausgleichen und die geringste Anzahl an Schulden übrig bleibt). 

Aus der Sicht der Gruppe sind alle Schulden beglichen, da die Schulden jetzt zwischen zwei einzelnen Personen bestehen.

Statt, dass der Schuldner dem Gläubiger den Betrag einfach überweist, können sich beide darauf einigen, diese Schuld an ein Spekulationsobjekt zu binden. So schlägt der Schuldner 2 Spekulationsobjekte vor (Wertpapiere, Kryptokurse, Währungskurse, Rohstoffpreise etc.), aus welchen der Gläubiger eines auswählen kann. Lehnt er beide ab, so kann der Schuldner erneut zwei vorschlagen, bis es zu einer Einigung kommt. 

Gibt es keine Einigung, so muss die Schuld einfach herkömmlich beglichen werden (Paypal oder Überweisung). Angenommen ein Spekulationsobjekt wurde Ausgewählt, so einigen sich beide Akteure noch auf einen Zeitraum über welchen die Schuld besteht. 
Der Wert der Schuld ist also über den ausgemachten Zeitraum an das Spekulationsobjekt gebunden. Zum Stichtag wird der neue Preis der Schuld anhand der Preisentwicklung des Spekulationsobjektes ermittelt. Dieser neue Preis wird in der App angezeigt und kann nun vom Schuldner über herkömmliche Art beglichen werden.


Bsp.: Eine Gruppe aus den Freunden A,B,C,D und E treffen sich in einer Bar. A gibt die erste Runde aus und zahlt 15 €. E muss danach los, weil er noch eine Hausarbeit zu schreiben hat. B zahlt die nächste Runde für 12 €. C hat Zigarren für 24€ gekauft, welche für A,B und C sind, D ist nicht-raucher.

Wird dieser Abend abgerechnet, ergibt sich, dass D 6€ an C schuldet, E 3€ an C schuldet, B 1€ an C schuldet und B 1 €an A schuldet. Man bemerke, dass Die Schulden so liegen, dass jeder genau so viel Zahlt, wie er konsumiert hat, wenn auch z.B. E nie etwas von C geliehen bekommen hat.

D schuldet also 6 € an C. D denkt, dass der Bitcoin Preis innerhalb der nächsten 2 Wochen fällt. C ist optimistischer und denkt, der Bitcoin Kurs steigt weiter. Sie einigen sich darauf, die Schuld für 2 Wochen an den Bitcoin Preis zu binden. 

Die App speichert diese Schuld jetzt also nicht als 6 €, sondern als einen Anteil eines Bitcoins, welcher zu dem Zeitpunkt 6€ Wert ist. Nach Ablauf der 2 Wochen wird der Wert des Anteils errechnet. Ist der Bitcoin Preis gestiegen, so sind die 6 € jetzt z.B. 7 € Wert, ist er gefallen, so ist die Schuld jetzt z.B. 4 € wert. Der neue Schuldbetrag wird jetzt D und C angezeigt. Den neuen Betrag kann D jetzt an C zahlen.



