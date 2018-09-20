## Google cloud

### IAM
IAM: Who has what access to which resource.

Principle
* least priveledge

Who
* Google account
    * individual
* Service account
    * application
* Google group
    * set of individual/app accounts
    * No login credentials
* G Suite domain
    * virtual group cover whole domain(G Suite)
* Cloud Identity domain
    * Like G suite domain, but has no access to apps and features
* allAuthenticatedUsers
    * excluding anonymous users
* allUsers

Which (Resources)
* hierarchy
    * org -> folder -> project -> compute engine instances ...
    
What (operations/permissions)
* verb on resource, usually correspond 1:1 with REST methods, but not always
* Role: collection of permissions. cannot assign a permission to a user directly.
    * Primitive roles: Owner, Editor, Viewer (historically available)
        * introduced prior to cloud IAM introduced.
    * Predefined roles. Such as Pub/Sub Publisher.
        * project roles
        * app engine roles
        * big query roles, cloud big table roles, ...
    * Custom roles.
    * IAM Policy
        * attached to a resource
        * contains a list of  bindings: members to role
        
    * Child policy cannot restrict parent policy