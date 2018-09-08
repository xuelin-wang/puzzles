Only defines attributes, not entity.
Each datom is five tuple: entity id, attribute, value, transaction id, op (added or retracted).

Architecture:
* Solo
    * client outside VPC
    * Primary compute, bastion server
    * Solo node to manage storage: indexing, transactions, caching
    * storage resources
* Production
    * client outside VPC
    * Primary compute, bastion server, network load balancer balancer
    * Nodes / groups of nodes to manage storage: indexing, transactions, caching
    * storage resources
    
    
```bash
http://entry.xw1.us-east-1.datomic.net:8182
```

