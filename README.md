# Invoking the SAP Cloud Platform Workflow Service APIs from outside of SAP Cloud Platform
This sample illustrates the essentials for calling a SAP Cloud Platform service API from a 
 Java Spring Boot application running outside of SAP Cloud Platform. This code targets the SAP
 Cloud Platform Workflow service, but the same essentials would apply to most SAP Cloud Platform
 service APIs.

**An Important Message Regarding Indirect Use of SAP APIs**   |
------------------------------------------------------------- |
There may be a licensing impact in using SAP APIs from an external application such as what's described in this code sample. This is especially true if API requests are being made on behalf of users who are not already covered by your SAP Licensing or Subscription agreements. You should consult [this document](https://www.sap.com/documents/2019/05/eadfa9d2-4a7d-0010-87a3-c30de2ffd8ff.html) and your local SAP representative to understand the business and licensing impact for production use of SAP APIs such as the SAP Cloud Platform Workflow Service described in this sample. |

# Overview
- [Invoking the SAP Cloud Platform Workflow Service APIs from outside of SAP Cloud Platform](#invoking-the-sap-cloud-platform-workflow-service-apis-from-outside-of-sap-cloud-platform)
- [Overview](#overview)
- [Description](#description)
- [Prerequisites](#prerequisites)
- [Quickstarts](#quickstarts)
  - [Quickstart for Running the Application Locally](#quickstart-for-running-the-application-locally)
  - [Quickstart for Creating Docker Image](#quickstart-for-creating-docker-image)
    - [Using `Dockerfile`](#using-dockerfile)
    - [Using Spring Boot](#using-spring-boot)
  - [Quickstart for Deploying Containerized Application to Local Kubernetes Cluster](#quickstart-for-deploying-containerized-application-to-local-kubernetes-cluster)
  - [Quickstart for Deploying Containerized Application to EKS](#quickstart-for-deploying-containerized-application-to-eks)
- [Resources](#resources)

# Description
This is a simple Spring Boot application capable of running in a range of environments. 
 It has been tested running locally on the developer's computer, in a local 
 Kubernetes cluster, and in cloud-based Kubernetes clusters.

The application exposes two API endpoints on port `8080`
- GET `/workflow/definition/list`
    - returns list of workflow definitions
- POST `/workflow/instance/new`
    - triggers the creation of a new workflow instance

Both endpoints connect to the SAP Cloud Platform Workflow Service. 
 To successfully make connection to the Workflow Service, application first
 makes a call to XSUAA, the XSA OAuth 2.0 authorization server, running on SAP
 Cloud Platform to request a JWT access token. Then, using the JWT token as an 
 `Authorization` header, Workflow APIs are invoked.

To make a successful call to both authorization server and Workflow Service,
 application requires four environment variables to be set: `UAA_URL`, 
 `UAA_CLIENTID`, `UAA_CLIENTSECRET`, and `ENDPOINTS_WORKFLOW_REST_URL`. 
 The exact values for these environment variables are instance-specific to the 
 XSUAA service running in your SAP Cloud Platform Cloud Foundry Space. Values 
 were issued by the Cloud Foundry Service Broker when the XSUAA instance was created.

Two Workflow API endpoints utilized in the application are 
 `/v1/workflow-definitions` and `/v1/workflow-instances` and already 
 have been defined in the [application.yml](./src/main/resources/application.yml).

**Note: This is a sample application that is being intentionally kept simple. 
 A production-ready version would require (at minimum) appropriate authorization 
 security for the application's REST API endpoints along with occasional refresh 
 of an expired JWT token**


# Prerequisites
Reasonably current versions of a Java JDK and Gradle are required to compile and run this sample.

Calling any SAP Cloud Platform service typically requires that you create an instance of that
 service first. If you don't have an instance of the Workflow service, you can use the exercises
 described in the [SAP Cloud Platform Workflow CodeJam](https://github.com/SAP-samples/cloud-workflow-codejam)
 to create the Workflow and XSUAA services required in this sample.

Successful completion of Cloud Workflow CodeJam should give you the following:
- Workflow Service running in SAP Cloud Platform
- An instance of the XSUAA authorization service
- User assigned with correct roles
- One Workflow Definition defined
- Values for the environment variables (the exact procedure for extracting these
  variables are described in [Step 06](https://github.com/SAP-samples/cloud-workflow-codejam/blob/64d2bbcfabf2cfcfe69ece789704716f19dc482b/exercises/06/readme.md#exercise-06---exploring-the-api-hub-and-the-workflow-api)
  of the CodeJam exercises)
    - `endpoints.workflow_rest_url`
    - `uaa.clientid`
    - `uaa.clientsecret`
    - `uaa.url`

If you already have an existing Workflow Service, you can probably still apply
 the instructions from [Step 06](https://github.com/SAP-samples/cloud-workflow-codejam/blob/64d2bbcfabf2cfcfe69ece789704716f19dc482b/exercises/06/readme.md#exercise-06---exploring-the-api-hub-and-the-workflow-api) 
 of the CodeJam exercises to extract the variable values you'll need.

# Quickstarts
1. [Quickstart for Running the Application Locally](#quickstart-for-running-the-application-locally)
1. [Quickstart for Creating Docker Image](#quickstart-for-creating-docker-image)
1. [Quickstart for Deploying Containerized Application to Local Kubernetes Cluster](#quickstart-for-deploying-containerized-application-to-local-kubernetes-cluster)
1. [Quickstart for Deploying Containerized Application to EKS](#quickstart-for-deploying-containerized-application-to-eks)


## Quickstart for Running the Application Locally
Please make sure you have completed the required [prerequisites](#prerequisites) before continuing.

1. Open up a command line tool of your choice
1. Clone the repo
    ```
    git clone https://github.com/SAP-samples/cloud-workflow-spring-sample.git
    ```
1. Change your current working directory to root of cloned repo
    ```
    cd cloud-workflow-spring-sample
    ```
1. Set four environment variables to appropriate values
    ```
    export UAA_URL='https://xxxxxxxxxxxxxxxxxx.authentication.eu10.hana.ondemand.com'
    export UAA_CLIENTID='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
    export UAA_CLIENTSECRET='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
    export ENDPOINTS_WORKFLOW_REST_URL='https://xxxxxxxxxxxxxx/workflow-service/rest'
    ```
    - `UAA_URL` should be set to the value of `uaa.url`
    - `UAA_CLIENTID` should be set to the value of `uaa.clientid`
    - `UAA_CLIENTSECRET` should be set to the value of `uaa.clientsecret`
    - `ENDPOINTS_WORKFLOW_REST_URL` should be set to the value of `endpoints.workflow_rest_url`
1. Run application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
    ```
    ./gradlew clean build bootRun
    ```
1. Once application is up, you can validate that application is running by 
 navigating to `localhost:8080`. [Swagger UI](https://swagger.io/tools/swagger-ui/)
 have been added to this application to help visualize and interact with the two endpoints.
1. (Optional) Try out `/workflow/instance/new` endpoint with value from the
 [CodeJam](https://github.com/SAP-samples/cloud-workflow-codejam/tree/master/exercises/07#5-examine-the-rest-of-the-requests-details)
 and see if you get the same response.
    ```json
    {
      "definitionId" : "orderprocess",
      "context": {
        "request": {
          "Id": "HT-1003",
          "Quantity": 25
        }
      }
    }
    ```
1. After exploring, please don't forget to stop the running application.


## Quickstart for Creating Docker Image
There are two ways to create a docker image for spring boot project. Both requires
 [docker](https://docs.docker.com/get-docker/) to either create and/or validate the image.
 Please choose one of the following options to create an image, both options will
 create an image `sap-cloud-workflow-spring-sample:0.0.1-SNAPSHOT`.
1. [Using `Dockerfile`](#using-dockerfile)
1. [Using Spring Boot](#using-spring-boot)


### Using `Dockerfile`
1. Use the `Dockerfile` that's available in the project root directory to create an 
 image using a `jar` file located in `/builds/libs` directory. 
    ```
    docker build --build-arg JAR_FILE=/build/libs/*.jar -t sap-cloud-workflow-spring-sample:0.0.1-SNAPSHOT .
    ```
    **Note: Running `./gradlew clean build` builds a `jar` in `./builds/libs` folder.**

1. Run the following command to validate that image has been created successfully.
    ```
    docker images 
    ``` 
    
    Output should look similar to below
    ```
    REPOSITORY                          TAG                  IMAGE ID            CREATED             SIZE
    sap-cloud-workflow-spring-sample    0.0.1-SNAPSHOT       xxxxxxxxxxxx        49 seconds ago      317MB
    ```
1. Run the following command to validate the image. Running the image will produce same result
 as [running the app locally]((#quickstart-for-running-the-application-locally)). Once
 the app is running, you should be able to navigate to `localhost:8080` and see the same
 Swagger UI page as before. 
    ```
    docker run \
      -e UAA_URL='https://xxxxxxxxxxxxxxxxxx.authentication.eu10.hana.ondemand.com' \
      -e UAA_CLIENTID='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
      -e UAA_CLIENTSECRET='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
      -e ENDPOINTS_WORKFLOW_REST_URL='https://xxxxxxxxxxxxxx/workflow-service/rest' \
      -p 8080:8080 sap-cloud-workflow-spring-sample:0.0.1-SNAPSHOT
    ```
1. After exploring, please don't forget to stop the running image.


### Using Spring Boot
Spring Boot 2.3.0.RELEASE includes [buildpack](https://buildpacks.io/) support for both 
 Maven and Gradle which allows you to use `gradle`/`mvn` command to create an image
 without a `Dockerfile`.

1. Run the following to create an image using `gradlew` 
    ```
    ./gradlew bootBuildImage
    ```
1. Run the following command to validate that image has been created successfully.
    ```
    docker images 
    ``` 
    
    Output should look similar to below
    ```
    REPOSITORY                          TAG                  IMAGE ID            CREATED             SIZE
    sap-cloud-workflow-spring-sample    0.0.1-SNAPSHOT       xxxxxxxxxxxx        49 seconds ago      317MB
    ```
1. Run the following command to validate the image. Running the image will produce same result
 as [running the app locally](#quickstart-for-running-the-application-locally). Once
 the app is running, you should be able to navigate to `localhost:8080` and see the same
 Swagger UI page as before. 
    ```
    docker run \
      -e UAA_URL='https://xxxxxxxxxxxxxxxxxx.authentication.eu10.hana.ondemand.com' \
      -e UAA_CLIENTID='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
      -e UAA_CLIENTSECRET='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
      -e ENDPOINTS_WORKFLOW_REST_URL='https://xxxxxxxxxxxxxx/workflow-service/rest' \
      -p 8080:8080 sap-cloud-workflow-spring-sample:0.0.1-SNAPSHOT
    ```
1. After exploring, please don't forget to stop the running image.


## Quickstart for Deploying Containerized Application to Local Kubernetes Cluster
This quickstart requires a Kubernetes Cluster running locally and `kubectl` CLI

1. Set kubectl config with correct Kubernetes cluster.
1. Run the following command to create a secret `sap-cloud-workflow-spring-sample-secret`
 for environment variables to be used in the cluster.
    ```
    kubectl create secret generic sap-cloud-workflow-spring-sample-secret \
        --from-literal=UAA_URL='https://xxxxxxxxxxxxxxxxxx.authentication.eu10.hana.ondemand.com' \
        --from-literal=UAA_CLIENTID='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
        --from-literal=UAA_CLIENTSECRET='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
        --from-literal=ENDPOINTS_WORKFLOW_REST_URL='https://xxxxxxxxxxxxxx/workflow-service/rest'
    ```
1. Run the following command to create a deployment using the image and secret
 created in the previous step. [Deployment yaml file](./kubernetes/local/deployment.yaml)
 is provided for you to use.
    ```
    kubectl create -f kubernetes/local/deployment.yaml
    ```
1. Create a service to expose the deployment on port `8080`.
    ```
    kubectl expose deployment/sap-cloud-workflow-spring-sample --type=LoadBalancer --port=8080
    ```
1. Validate application is running and exposed by navigating to `localhost:8080`.
1. After exploring, clean up resources by deleting deployment, service and secret created.
    ```
    kubectl delete svc sap-cloud-workflow-spring-sample \
        && kubectl delete deployment sap-cloud-workflow-spring-sample \
        && kubectl delete secret sap-cloud-workflow-spring-sample-secret
    ```


## Quickstart for Deploying Containerized Application to EKS
You were able to use a local image for your local Kubernetes cluster, but once you start
 using Kubernetes service from different hyperscalers, you will need to start thinking about
 where to pull your images from. 
 
There are two types of registries you can choose from
1. A public registry like [Docker Hub](https://hub.docker.com/)
1. A private registry like [AWS Elastic Container Registry](https://aws.amazon.com/ecr/),
 [Azure Container Registry](https://azure.microsoft.com/en-us/services/container-registry/),
 [Google Container Registry](https://cloud.google.com/container-registry), and 
 [more](https://kubernetes.io/docs/concepts/containers/images/#using-a-private-registry)

Below is an example of how to upload image created in a previous step into ECR. 
 Please upload an image to either a public or private registry before continuing.
 If an image is uploaded to a public registry, please skip creating docker-registry
 secret from the steps below.

```bash
AWS_ACCOUNT_ID=<aws account id>
REGION=<region>

aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com

aws ecr create-repository --repository-name sap-cloud-workflow-spring-sample-aws-ecr --image-scanning-configuration scanOnPush=true --region $REGION

docker tag sap-cloud-workflow-spring-sample-aws-ecr:0.0.1-SNAPSHOT $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/sap-cloud-workflow-spring-sample-aws-ecr:0.0.1-SNAPSHOT

docker push $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/sap-cloud-workflow-spring-sample-aws-ecr:0.0.1-SNAPSHOT
```

Once uploaded to a container registry, follow the same steps as local Kubernetes cluster to
 deploy the sample application. Differences between deploying to local and cloud cluster are
 creating additional secret to pull an image from a private registry and a change to 
 `deployment.yaml` to use the new secret.

1. Create a secret `sap-cloud-workflow-spring-sample-aws-ecr` to pull images from a private container registry.
    ```
    AWS_ACCOUNT_ID=<aws account id>
    REGION=<region>
    EMAIL=<email>
    
    kubectl create secret docker-registry sap-cloud-workflow-spring-sample-aws-ecr \
        --docker-server=https://$AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com \
        --docker-username=AWS \
        --docker-password=$(aws ecr get-login-password --region $REGION) \
        --docker-email=$EMAIL
    ```
1. Create a secret `sap-cloud-workflow-spring-sample-secret` for environment variables to 
 be used in the cluster.
    ```
    kubectl create secret generic sap-cloud-workflow-spring-sample-secret \
        --from-literal=UAA_URL='https://xxxxxxxxxxxxxxxxxx.authentication.eu10.hana.ondemand.com' \
        --from-literal=UAA_CLIENTID='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
        --from-literal=UAA_CLIENTSECRET='xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' \
        --from-literal=ENDPOINTS_WORKFLOW_REST_URL='https://xxxxxxxxxxxxxx/workflow-service/rest'
    ```
1. Open up `deployment.yaml` file under `/kubernetes/aws/` directory and replace `<aws account id>`
    with your own. Then, run the following to create a deployment.
    ```
    kubectl create -f kubernetes/aws/deployment.yaml
    ```

   **Note: directory of deployment.yaml have changed from /local to /aws** 

1. Create a service to expose the deployment.
    ```
    kubectl expose deployment/sap-cloud-workflow-spring-sample --type=LoadBalancer --port=8080
    ```
1. Validate application is running and exposed by navigating to external-ip allocated by the cloud provider.
    Run the following to grab the external ip/address and navigate to it on port 8080 to see same
    swagger page as before.
    ```
    kubectl get svc sap-cloud-workflow-spring-sample -o jsonpath={.status.loadBalancer.ingress[].hostname}
    ```
   
    **Note: Assigning an external IP can take more than a minute**
1. After exploring, clean up resources by deleting deployment, service and secret created.
    ```
    kubectl delete svc sap-cloud-workflow-spring-sample \
        && kubectl delete deployment sap-cloud-workflow-spring-sample \
        && kubectl delete secret sap-cloud-workflow-spring-sample-secret \
        && kubectl delete secret sap-cloud-workflow-spring-sample-aws-ecr
    ```

# Resources
- [What is SAP Cloud Platform Workflow](https://help.sap.com/viewer/16e4ca742bd742e98184ef1e53d2ec2d/Cloud/en-US/a3220658f2dc46a48e47614aa2a2c663.html)
- [API Business Hub - Workflow Endpoints](https://api.sap.com/api/SAP_CP_Workflow_CF/resource)

# License
Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved. 
This file is licensed under the Apache Software License, version 2.0 except as noted otherwise in the [LICENSE](LICENSE) file.
