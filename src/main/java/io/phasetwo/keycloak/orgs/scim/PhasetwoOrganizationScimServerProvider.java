package io.phasetwo.keycloak.orgs.scim;

import fi.metatavu.keycloak.scim.server.config.ConfigurationError;
import fi.metatavu.keycloak.scim.server.organization.*;
import io.phasetwo.service.model.OrganizationModel;
import io.phasetwo.service.model.OrganizationProvider;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import java.net.URI;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

public class PhasetwoOrganizationScimServerProvider implements OrganizationScimServerProvider {

  protected final KeycloakSession session;

  public PhasetwoOrganizationScimServerProvider(KeycloakSession session) {
    this.session = session;
  }

  @Override
  public OrganizationScimServer getScimServer(KeycloakSession session) {
    return new OrganizationScimServer(session) {
      @Override
      public OrganizationScimContext getScimContext(
          KeycloakSession session, String organizationId) {
        return createScimContext(session, organizationId);
      }
    };
  }

  public static final String PHASETWO_ORGANIZATION_SESSION_ATTRIBUTE = "ext-phasetwo-organization";

  private static OrganizationScimContext createScimContext(
      KeycloakSession session, String organizationId) {
    RealmModel realm = session.getContext().getRealm();
    if (realm == null) {
      throw new NotFoundException("Realm not found");
    }

    final OrganizationModel organization =
        session.getProvider(OrganizationProvider.class).getOrganizationById(realm, organizationId);

    if (organization == null) {
      throw new NotFoundException("Organization not found");
    }

    session.setAttribute(PHASETWO_ORGANIZATION_SESSION_ATTRIBUTE, organization);

    URI baseUri =
        session
            .getContext()
            .getUri()
            .getBaseUri()
            .resolve(
                String.format(
                    "realms/%s/scim/v2/organizations/%s/", realm.getName(), organization.getId()));

    String id = organization.getId();
    ComponentModel model = session.getContext().getRealm().getComponent(id);
    if (model == null) {
      throw new NotFoundException(id + " unknown");
    }
    OrganizationScimConfig config = new ComponentScimConfig(model);
    if (!config.isEnabled()) {
      throw new NotFoundException(id + " is currently disabled");
    }

    try {
      config.validateConfig();
    } catch (ConfigurationError e) {
      throw new InternalServerErrorException("Invalid SCIM configuration", e);
    }

    return new PhasetwoOrganizationScimContext(baseUri, session, realm, config, organization);
  }
}
