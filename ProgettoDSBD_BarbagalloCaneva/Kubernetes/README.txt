#L'applicazione è stata sviluppata e testata su una macchina con Fedora 36, utilizzando un cluster minikube con driver kvm2, 6GB di RAM e 2CPU.
1. Avviare cluster minikube con il comando:
   minikube start --memory=6144 --cpus=2 --vm-driver=kvm2
2. Eseguire il seguente comando per utilizzare il daemon docker di minikube:
   eval $(minikube docker-env)
3. Spostarsi nella directory "ProgettoDSBD_BarbagalloCaneva/ProgettoDSBD_BarbagalloCaneva/Kubernetes/" e buildare le immagini docker con i seguenti comandi:
   docker build -t frontend Frontend/
   docker build -t reglog RegLogIn/RegLogService/
   docker build -t receptionservice Reception/ReceptionService/
   docker build -t docservice Doc/DoctorService/
4. Abilitare addon dell'ingress:
   minikube addons enable ingress
5. Avviare i la creazione dei deploy e dei services:
   kubectl apply -f k8s
6. Creare namespace monitoring:
   kubectl create namespace monitoring
7. Spostarsi nella directory "k8s" ed eseguire i seguenti comandi per avviare la creazione dei services e dei deploy di monitoring:
   kubectl apply -f prometheus
8. Inserire nel proprio file di risoluzione degli indirizzi IP locali, l'indirizzo IP dell'ingress, con il seguente comando:
   echo "$(minikube ip) hospital.loc" | sudo tee -a /etc/hosts
9. Aprire il browser e contattare il frontend all'URL "http://hospital.loc/"
 
# A questo punto,navigare le pagine web e per un corretto funzionamento dell'applicazione, eseguire in ordine:
  1. Registrare un medico
  2. Eseguire Logout
  3. Registrare un receptionist
  4. Inserire un paziente per quel medico
  5. Eseguire Logout
  6. Eseguire Login con le credenziali del medico prima registrato
  7. Cercare il paziente precedentemente inserito e visualizzare/modificare la sua cartella clinica.
  NOTA: effettuare un ricovero del paziente per testare la transazione SAGA:
        lo scenario di fallimento si ha quando si hanno 5 pazienti ricoverati e si tenta il ricovero del sesto paziente.
         
# Per visualizzare l'interfaccia grafica di monitoraggio, digitare i comandi:
  minikube service --namespace=monitoring grafana
  minikube service --namespace=monitoring prometheus
