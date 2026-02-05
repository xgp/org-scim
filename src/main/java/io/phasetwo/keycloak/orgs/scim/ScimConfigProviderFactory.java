package io.phasetwo.keycloak.orgs.scim;

import com.google.auto.service.AutoService;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;

@JBossLog
@AutoService(UserStorageProviderFactory.class)
public class ScimConfigProviderFactory implements UserStorageProviderFactory<ScimConfigProvider> {

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return ComponentScimConfig.getConfigProperties();
  }

  @Override
  public ScimConfigProvider create(KeycloakSession session, ComponentModel model) {
    return new ScimConfigProvider(session, model);
  }

  @Override
  public String getId() {
    return "Organization SCIM";
  }

  @Override
  public String getHelpText() {
    return "Organization SCIM v2";
  }

  @Override
  public void validateConfiguration(
      KeycloakSession session, RealmModel realm, ComponentModel model) {
    log.info("ScimConfigProviderFactory validateConfiguration");
    log.infof("validationConfiguration id = %s", model.getId());
    ComponentScimConfig config = new ComponentScimConfig(model);
    config.setId(config.getOrganizationId());
  }

  @Override
  public void onCreate(KeycloakSession session, RealmModel realm, ComponentModel model) {
    log.info("ScimConfigProviderFactory onCreate");
  }

  @Override
  public void onUpdate(
      KeycloakSession session, RealmModel realm, ComponentModel oldModel, ComponentModel newModel) {
    log.info("ScimConfigProviderFactory onUpdate");
    ComponentScimConfig oldConfig = new ComponentScimConfig(newModel);
    ComponentScimConfig newConfig = new ComponentScimConfig(newModel);

    // realm.updateComponent(newModel);
  }
}
