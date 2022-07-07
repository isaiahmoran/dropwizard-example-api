package com.moran.dropwizardtest.models;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.time.OffsetDateTime;

@HubSpotStyle
@Value.Immutable
public interface TestModelIF extends TestModelCore {
    int getId();
    OffsetDateTime getCreatedAt();
    OffsetDateTime getUpdatedAt();
}
