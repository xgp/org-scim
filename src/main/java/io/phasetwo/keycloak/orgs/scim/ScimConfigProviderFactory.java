package io.phasetwo.keycloak.orgs.scim;

import com.google.auto.service.AutoService;
import io.phasetwo.service.model.OrganizationModel;
import io.phasetwo.service.model.OrganizationProvider;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;

@SuppressWarnings("rawtypes")
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
    log.debug("ScimConfigProviderFactory validateConfiguration");

    // set component ID = organizaiton ID
    ComponentScimConfig config = new ComponentScimConfig(model);
    if (model.getId() == null) {
      // check for another component with the same ID
      ComponentModel otherModel =
          session.getContext().getRealm().getComponent(config.getOrganizationId());
      if (otherModel != null) {
        throw new ComponentValidationException(
            "Another SCIM provider already exists for organization ID: "
                + config.getOrganizationId());
      }
      config.setId(config.getOrganizationId());
    }

    // organization must exist
    OrganizationModel organization =
        session.getProvider(OrganizationProvider.class).getOrganizationById(realm, config.getId());
    if (organization == null) {
      throw new ComponentValidationException(
          "Organization ID not found: " + config.getOrganizationId());
    }
  }

  @Override
  public void onCreate(KeycloakSession session, RealmModel realm, ComponentModel model) {
    log.debug("ScimConfigProviderFactory onCreate");
  }

  @Override
  public void onUpdate(
      KeycloakSession session, RealmModel realm, ComponentModel oldModel, ComponentModel newModel) {
    log.debug("ScimConfigProviderFactory onUpdate");
  }
}
