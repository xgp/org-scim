> :bug: **This is alpha software**

# Phase Two Orgs SCIM provider

A organization SCIM provider implementation that uses this [Keycloak SCIM 2.0 Extension](https://github.com/Metatavu/keycloak-scim-server/) as the base. Currently, this requires unmerged functionality in that project, which can be pulled from this [PR](https://github.com/Metatavu/keycloak-scim-server/pull/47/)

## Installing

In order to use it, you need to get the jar from that project (or our copy in the `libs/` dir, as they do not publish to Maven Central), and the jar that is built by `mvn package` in this project. Both will go in your Keycloak `providers/` dir.

You will need to set the SPI variable `--spi-realm-restapi-extension--scim--organization-type=phasetwo` in order to load this implementation rather than they Keycloak native orgs default.

## Conf

Configuration on organization level is done by defining organization attributes in the Keycloak server. See the documentation here on how to set the attributes: https://github.com/Metatavu/keycloak-scim-server/?tab=readme-ov-file#configuration-on-organization-level

-----

All documentation, source code and other files in this repository are Copyright 2026 Phase Two, Inc.
