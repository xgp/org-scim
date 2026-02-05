> :bug: **This is alpha software**

# Phase Two Orgs SCIM provider

A organization SCIM provider implementation that uses this [Keycloak SCIM 2.0 Extension](https://github.com/Metatavu/keycloak-scim-server/) as the base. Currently, this requires unmerged functionality in that project, which can be pulled from this [PR](https://github.com/Metatavu/keycloak-scim-server/pull/47/)

## Installing

In order to use it, you need to get the jar from that project (or our copy in the `libs/` dir, as they do not publish to Maven Central), and the jar that is built by `mvn package` in this project. Both will go in your Keycloak `providers/` dir.

You will need to set the SPI variable `-spi-realm-restapi-extension-scim-organization-type=phasetwo` in order to load this implementation rather than they Keycloak native orgs default.

## Conf

Configuration on organization level is done via the `ComponentModel` interface. This gives you a "free" GUI in the **User Federation** section of the Keycloak Admin UI. Pick the "Organization SCIM" provider. Note that you must manually enter the organization ID you wish to configure. 

<img width="1226" height="732" alt="image" src="https://github.com/user-attachments/assets/c822acde-4ca0-43bd-9417-77127de9088a" />

------

All documentation, source code and other files in this repository are Copyright 2026 Phase Two, Inc.
