#All'interno della directory DockerCompose eseguire i seguenti comandi:
docker-compose up --build


#dopo aver avviato i container aprire il proprio browser e digitare nella barra degli indirizzi:
http://localhost:80/

#navigare le pagine web e per un corretto funzionamento dell'applicazione, eseguire in ordine:
1. Registrare un medico
2. Eseguire Logout
3. Registrare un receptionist
4. Inserire un paziente per quel medico
5. Eseguire Logout
6. Eseguire Login con le credenziali del medico prima registrato
7. Cercare il paziente precedentemente inserito e visualizzare/modificare la sua cartella clinica.
   NOTA: effettuare un ricovero del paziente per testare la transazione SAGA:
         lo scenario di fallimento si ha quando si hanno 5 pazienti ricoverati e si tenta il ricovero del sesto paziente.
