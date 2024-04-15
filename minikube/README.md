minikube
========

* https://minikube.sigs.k8s.io/docs/tutorials/

## minikube commands

* ```minikube version```
* ```minikube start```
* ```minikube status```
* ```minikube stop```
* ```minikube node add```
* ```minikube service list```
* ```minikube stop```

Additionally:

* ```minikube start --nodes 3``` - start k8s with 3 nodes

## rack & node affinity testing

* https://kubernetes.io/docs/concepts/scheduling-eviction/topology-spread-constraints/
* https://docs.openshift.com/container-platform/4.14/nodes/scheduling/nodes-scheduler-pod-affinity.html#nodes-scheduler-pod-affinity-example-antiaffinity_nodes-scheduler-pod-affinity

Setup rack labels:

```shell
kubectl label nodes minikube node-role.kubernetes.io=tst-wkr-rack1
kubectl label nodes minikube-m02 node-role.kubernetes.io=tst-wkr-rack2
kubectl label nodes minikube-m03 node-role.kubernetes.io=tst-wkr-rack3
kubectl label nodes minikube-m04 node-role.kubernetes.io=tst-wkr-rack1
```

Setup node labels:

```shell
kubectl label nodes minikube node.kubernetes.io/tst-wkr=tst-wkr1
kubectl label nodes minikube-m02 node.kubernetes.io/tst-wkr=tst-wkr2
kubectl label nodes minikube-m03 node.kubernetes.io/tst-wkr=tst-wkr3
kubectl label nodes minikube-m04 node.kubernetes.io/tst-wkr=tst-wkr4
```

Additional commands:

* ```kubectl get nodes --show-labels```
* ```kubectl get pods -o wide```

Affinity snippet used in different service:

```yaml
affinity:
  nodeAffinity:
    requiredDuringSchedulingIgnoredDuringExecution:
      nodeSelectorTerms:
        - matchExpressions:
            - key: <label-key>
              operator: In
              values:
```
