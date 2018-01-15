### Amazon Web Services (AWS)
#### IAM: Identity access management
Identities: Integration with existing identity management systems (Identity federation)

Resources: EC2(Elastic compute cloud), S3 (Simple Storage Service), DynamoDB, Redshift etc.

Access permissions: granular, from complete access to subset of resources, such as Se3 buckets, EC2 instances, billing information etc.

CloudTrail

Eventually Consistent

Accessing IAM
* AWS management console
* CLI
* AWS SDK
* IAM HTTPS API

#### AWS API
* IAM
<pre>
To grant external account access:
external account id, policies/role.
</pre>
```
user, role, group, policy
instance profile for role,
id, arn, path, name
API:
{create, delete, update}{user, role, policy, group}
list{users, groups, roles}
list[attached]{user, group, role}policies

```

* EC2
```
start, stop, reboot instances
{create, modify, delete}:
        network-acl, network-interface, network-interface-permission, route, route-table
        security-group, subnet, vpc, vpc-endpoint, vpc-perring-connection, vpn-connection

associate vpc cidr-block

```

#### AWS services
* EC2
Complete control of instances, os can be multiple linux and windows servers. 
Autoscaling. Secure.

* Constainer registry and services
Manages the cluster for you. Just need define task which specify container image, 
how to connect to other containers, number of instances etc.

* VPC
Can configure your own subnets, external connections, VPN, route tables etc.

* Lightsail
Easiest way to deploy app with needed resources such as computer, storage etc.
Offers multiple OSes. Can ssh to install your own package. Stop does not erase
instance status.

* More services

 |category| Service Name | Description |
 |--------|--------------|----------------------------------------------|
 |Compute|Batch         |Schedule, resources management for batch jobs |
 |Compute|Elastic Beanstalk|Best way to deploy code. Just need upload code. Handles deployment for you.|
 |Compute|Lambda        |Run code in provided options of envs. Zero admin |
 |Storage|S3            |Objects store. Each object is file + metadata. bucket is object container|
 |Storage|EBS: Elastic block storage|Fine tuned performance. Used by storage engines.|
 |Storage|EFS: Elastic file system|Cloud file system|
 |Storage|Amazon Glacier|Cheap, for archive and long term storage|
 |Storage|Storage gateway|Hibrid storage solution|
 |Storage|Snowball,sbnowball edge, snowmobile|Transfer files in/out of AWS storage|
 |Database|Aurora|Mysql and postgresql compatible|
 |Database|RDS           |option of Aurora, PostgreSQL, MySQL, MariaDB, Oracle, and Microsoft SQL Server.|
 |Database|Dynamo       |Nosql database|
 |Database|DAX:Danymo accelerator|Cache for Dynamo|
 |Database|Elastic cache|support Redis or memcached. Cache layer for app.|
 |Database|Redshift      |Data warehouse solution. Can run sql query against S3 unstructured data.|
 |Network |Couldfront   |content delivery network|
 |Management|Cloudwatch |Monitor resoures, logs. collect, track, alert metrics|
 |Management|Cloudtrail |Governance, compliance, operational, risk auditing. |
 |Management|Opsworks   |Use chef. Configuration as code |


