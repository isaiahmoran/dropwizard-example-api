package com.moran.dropwizardtest.models;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public interface PingIF {
    String getPing();
}
