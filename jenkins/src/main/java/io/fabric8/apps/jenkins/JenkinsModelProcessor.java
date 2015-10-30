/*
 * Copyright 2005-2015 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.fabric8.apps.jenkins;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.generator.annotation.KubernetesModelProcessor;
import io.fabric8.openshift.api.model.TemplateBuilder;

import javax.inject.Named;

@KubernetesModelProcessor
public class JenkinsModelProcessor {

    @Named("jenkins")
    public void on(ContainerBuilder builder) {
        builder.withNewLifecycle()
                .withNewPostStart()
                .withNewExec()
                .addToCommand("/root/postStart.sh")
                .endExec()
                .endPostStart()
                .endLifecycle()
                .build();
    }

    public void on(TemplateBuilder builder) {
        String version = System.getProperty("project.version");
        String versionPostfix = "";
        if (version != null && version.length() > 0 && !version.endsWith("SNAPSHOT")) {
            versionPostfix = ":" + version;
        }
        String imageName = "fabric8/jenkins-swarm-client" + versionPostfix;
        builder.addNewReplicationControllerObject()
                .withNewMetadata()
                    .withName("jenkins-swarm-client")
                .endMetadata()
                .withNewSpec()
                    .withReplicas(1)
                    .addToSelector("provider", "fabric8")
                    .addToSelector("component", "jenkins-swarm-client")
                    .withNewTemplate()
                        .withNewMetadata()
                            .addToLabels("provider", "fabric8")
                            .addToLabels("component", "jenkins-swarm-client")
                        .endMetadata()
                        .withNewSpec()
                            .addNewContainer()
                                .withNewLifecycle()
                                    .withNewPostStart()
                                        .withNewExec()
                                            .addToCommand("/var/jenkins_home/postStart.sh")
                                        .endExec()
                                    .endPostStart()
                                .endLifecycle()
                                .withName("jenkins-swarm-client")
                                .withImage(imageName)
                                .withNewSecurityContext()
                                    .withPrivileged(true)
                                .endSecurityContext()
                                .addNewVolumeMount()
                                    .withName("jenkins-workspace")
                                    .withMountPath("/var/jenkins_home/workspace")
                                    .withReadOnly(false)
                                .endVolumeMount()
                                .addNewPort()
                                    .withName("http")
                                    .withContainerPort(8080)
                                .endPort()
                                .addNewEnv()
                                    .withName("DOMAIN")
                                    .withValue("${DOMAIN}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_WORKFLOW_GIT_REPOSITORY")
                                    .withValue("${JENKINS_WORKFLOW_GIT_REPOSITORY}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_WORKFLOW_GIT_REPOSITORY")
                                    .withValue("${JENKINS_WORKFLOW_GIT_REPOSITORY}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("SONATYPE_PASSWORD")
                                    .withValue("${SONATYPE_PASSWORD}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("GITHUB_TOKEN")
                                    .withValue("${GITHUB_TOKEN}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("DOCKER_REGISTRY_USERNAME")
                                    .withValue("${DOCKER_REGISTRY_USERNAME}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_GOGS_EMAIL")
                                    .withValue("${JENKINS_GOGS_EMAIL}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("DOCKER_REGISTRY_SERVER_ID")
                                    .withValue("${DOCKER_REGISTRY_SERVER_ID}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("DOCKER_REGISTRY_PASSWORD")
                                    .withValue("${DOCKER_REGISTRY_PASSWORD}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("DOCKER_REGISTRY_PASSWORD")
                                    .withValue("${DOCKER_REGISTRY_PASSWORD}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("DOCKER_REGISTRY")
                                    .withValue("${DOCKER_REGISTRY_PREFIX}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_JOBS_GIT_REPOSITORY")
                                    .withValue("${JENKINS_JOBS_GIT_REPOSITORY}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("CGO_ENABLED")
                                    .withValue("${CGO_ENABLED}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("FABRIC8_DEFAULT_ENVIRONMENTS")
                                    .withValue("${FABRIC8_DEFAULT_ENVIRONMENTS}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("SONATYPE_USERNAME")
                                    .withValue("${SONATYPE_USERNAME}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_GOGS_PASSWORD")
                                    .withValue("${JENKINS_GOGS_PASSWORD}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("JENKINS_GOGS_USER")
                                    .withValue("${JENKINS_GOGS_USER}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("GPG_PASSPHRASE")
                                    .withValue("${GPG_PASSPHRASE}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("GITHUB_AUTH_TOKEN")
                                    .withValue("${GITHUB_AUTH_TOKEN}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("GITHUB_AUTH_TOKEN")
                                    .withValue("${GITHUB_AUTH_TOKEN}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("NEXUS_USERNAME")
                                    .withValue("${NEXUS_USERNAME}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("NEXUS_PASSWORD")
                                    .withValue("${NEXUS_PASSWORD}")
                                .endEnv()
                                .addNewEnv()
                                    .withName("KUBERNETES_TRUST_CERTIFICATES")
                                    .withValue("true")
                                .endEnv()
                                .addNewEnv()
                                    .withName("KUBERNETES_NAMESPACE")
                                    .withNewValueFrom()
                                        .withNewFieldRef()
                                            .withFieldPath("metadata.namespace")
                                        .endFieldRef()
                                    .endValueFrom()
                                .endEnv()
                                .addNewVolumeMount()
                                    .withMountPath("/var/jenkins/.ssh-git")
                                    .withName("git-ssh")
                                    .withReadOnly(false)
                                .endVolumeMount()
                                .addNewVolumeMount()
                                    .withMountPath("/root/.gnupg")
                                    .withName("gpg-key")
                                .withReadOnly(false)
                                .endVolumeMount()
                                .addNewVolumeMount()
                                    .withMountPath("/home/jenkins/ssh-keys")
                                    .withName("jenkins-master-key")
                                .withReadOnly(false)
                                .endVolumeMount()
                                .addNewVolumeMount()
                                    .withMountPath("/var/run/docker.sock")
                                    .withName("docker-socket")
                                .withReadOnly(false)
                                .endVolumeMount()
                            .endContainer()
                            .addNewVolume()
                                .withName("jenkins-workspace")
                                .withNewHostPath("/var/jenkins_home/workspace")
                            .endVolume()
                            .addNewVolume()
                                .withName("git-ssh")
                                .withNewSecret("jenkins-git-ssh")
                            .endVolume()
                            .addNewVolume()
                                .withName("gpg-key")
                                .withNewSecret("jenkins-release-gpg")
                            .endVolume()
                            .addNewVolume()
                                .withName("jenkins-master-key")
                                .withNewSecret("jenkins-master-ssh")
                            .endVolume()
                            .addNewVolume()
                                .withName("docker-socket")
                                .withNewHostPath("/var/run/docker.sock")
                            .endVolume()
                            .withServiceAccountName("jenkins")
                        .endSpec()
                    .endTemplate()
                .endSpec()
                .and()

                .addNewServiceObject()
                .withNewMetadata()
                    .withName("jenkins-agent")
                        .addToLabels("component", "jenkins-agent")
                        .addToLabels("provider", "fabric8")
                    .endMetadata()
                .withNewSpec()
                .withType("LoadBalancer")
                .addNewPort()
                    .withName("http")
                    .withProtocol("TCP")
                    .withPort(80)
                    .withNewTargetPort(8080)
                .endPort()
                .addNewPort()
                    .withName("agent")
                    .withProtocol("TCP")
                    .withPort(50000)
                    .withNewTargetPort(50000)
                .endPort()
                    .addToSelector("component", "jenkins")
                    .addToSelector("provider", "fabric8")
                .endSpec()
                .endServiceObject()

                .build();

    }
}
