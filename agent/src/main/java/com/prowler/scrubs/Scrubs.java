package com.prowler.scrubs;

import com.prowler.agent.Reporter;
import java.util.List;
import jersey.repackaged.com.google.common.collect.ImmutableList;

/**
 * Apply scrubs
 */
public final class Scrubs {

  private final Reporter reporter;

  public Scrubs(Reporter reporter) {
    this.reporter = reporter;
  }

  private static final Scrub EMAIL_SCRUB = new EmailScrub();
  private static final Scrub IP_ADDRESS_SCRUB = new InetAddressScrub();
  private static final Scrub CREDIT_CARD_SCRUB = new CreditCardScrub();

  private final List<Scrub> scrubs = ImmutableList.of(EMAIL_SCRUB, IP_ADDRESS_SCRUB, CREDIT_CARD_SCRUB);

  public void scrub(String text) {
    for (Scrub scrub : scrubs) {
      if (scrub.containsSensitiveInformation(text)) {
        reporter.reportLogLine(scrub.redact(text), scrub.violationType());
      }
    }
  }
}
